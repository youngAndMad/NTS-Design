package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.LogCommandDTO;
import danekerscode.technicaltask.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("log")
public class LogController {

    private final LogService logService;

    @GetMapping("{id}")
    ResponseEntity<?> getUserLogs(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity
                .ok(logService.findUserLogs(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(
            @RequestParam("user_id") Long userId,
            @RequestBody LogCommandDTO dto
    ) {
        logService.add(dto, userId);
    }
}
