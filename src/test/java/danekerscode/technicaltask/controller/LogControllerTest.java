package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.LogCommandDTO;
import danekerscode.technicaltask.model.Log;
import danekerscode.technicaltask.service.LogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogControllerTest {

    @Mock
    private LogService logService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private LogController logController;

    @Test
    void testAddLogWhenCalledThenLogServiceAddIsCalled() {
        var dto = new LogCommandDTO("command", "content", 1L);

        logController.addLog(dto);

        verify(logService).add(dto);
    }

    @Test
    void testSendLogsWhenCommandIsNotLogsThenReturnWithoutPerformingAnyOperations() {
        var dto = new LogCommandDTO("notLogs", "content", 1L);

        logController.sendLogs(dto);

        verify(logService, never()).findUserLogs(any());
        verify(messagingTemplate, never()).convertAndSend(anyString(), Optional.ofNullable(any()));
    }

    @Test
    void testSendLogsWhenCommandIsLogsThenFindUserLogsAndConvertAndSend() {
        var dto = new LogCommandDTO("logs", "content", 1L);
        var logs = new ArrayList<Log>();

        when(logService.findUserLogs(dto.userId())).thenReturn(logs);

        logController.sendLogs(dto);

        verify(logService).findUserLogs(dto.userId());
        verify(messagingTemplate).convertAndSend("/topic/getLogs/" + dto.userId(), logs);
    }
}