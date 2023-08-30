package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.FileCommandDTO;
import danekerscode.technicaltask.service.AmazonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.amazonaws.services.s3.Headers.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final AmazonFileService service;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("simple-upload")
    ResponseEntity<?> simpleUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("user_id") Long userId,
            @RequestParam("file_name") String fileName
    ) {
        return ResponseEntity.
                status(201) // HttpStatus Created
                .body(service.upload(userId, file, fileName));
    }

    @GetMapping("{id}")
    ResponseEntity<?> find(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(service.findById(id));
    }

    @MessageMapping("/fileInfo")
    void onConnection(
            FileCommandDTO dto
    ) {
        var file = service.findByName(dto.fileName());
        messagingTemplate.convertAndSend("/topic/fileInfo/" + dto.userId(), file);
    }

    @GetMapping("download")
    ResponseEntity<?> download(
            @RequestParam Long id
    ) {
        var file = service.findById(id);
        var content = service.download(id);
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(content));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }


}
