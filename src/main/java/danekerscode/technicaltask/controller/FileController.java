package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.service.AmazonFileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final AmazonFileService service;

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


}
