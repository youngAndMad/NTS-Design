package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.LogCommandDTO;
import danekerscode.technicaltask.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("log")
public class LogController {

    private final LogService logService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/addLog")
    void addLog(
            LogCommandDTO dto
    ) {
        logService.add(dto);
    }

    @MessageMapping("/getLogs")
    void sendLogs(
            LogCommandDTO dto
    ) {
        if (!"logs".equals(dto.command())) {
            return;
        }

        var logs = logService.findUserLogs(dto.userId());

        messagingTemplate.convertAndSend("/topic/getLogs/" + dto.userId(), logs);
    }
}
