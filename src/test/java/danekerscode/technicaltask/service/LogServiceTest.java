package danekerscode.technicaltask.service;

import danekerscode.technicaltask.dto.LogCommandDTO;
import danekerscode.technicaltask.mapper.LogMapper;
import danekerscode.technicaltask.model.Log;
import danekerscode.technicaltask.model.User;
import danekerscode.technicaltask.repository.LogRepository;
import danekerscode.technicaltask.service.impl.LogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private LogMapper logMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private LogServiceImpl logService;


    @Test
    public void testAddLog() {
        var user = new User();
        user.setId(1L);
        var dto = new LogCommandDTO(
                "addLogs",
                "hii!",
                1L
        );
        var log = new Log();

        when(userService.findById(dto.userId())).thenReturn(user);

        when(logMapper.toModel(dto.content(), user)).thenReturn(log);

        logService.add(dto);

        verify(userService).findById(dto.userId());
        verify(logMapper).toModel(dto.content(), user);
        verify(logRepository).save(log);
    }

    @Test
    public void findUserLogs() {
        var userId = 1L;
        var logs = Collections.singletonList(new Log());

        when(logRepository.findAllByOwnerId(userId)).thenReturn(logs);

        var result = logService.findUserLogs(userId);

        assertEquals(logs, result);
    }

    @Test
    public void findUserLogsShouldReturnEmptyList() {
        var userId = 1L;

        when(logRepository.findAllByOwnerId(userId)).thenReturn(Collections.emptyList());

        var result = logService.findUserLogs(userId);

        assertTrue(result.isEmpty());
    }


}
