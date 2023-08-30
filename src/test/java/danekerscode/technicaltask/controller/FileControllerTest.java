package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.FileCommandDTO;
import danekerscode.technicaltask.model.AmazonFile;
import danekerscode.technicaltask.service.AmazonFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FileControllerTest {

    @Mock
    private AmazonFileService service;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private FileController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new FileController(service, messagingTemplate);
    }

    @Test
    public void testSimpleUploadWhenParametersAreValidThenReturnCreatedStatus() {
        var file = new MockMultipartFile("file", "Hello, World!".getBytes());
        var userId = 1L;
        var fileName = "test.txt";

        when(service.upload(anyLong(), any(), anyString())).thenReturn(null);

        var response = controller.simpleUpload(file, userId, fileName);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void testSimpleUploadWhenParametersAreValidThenServiceUploadIsCalled() {
        var file = new MockMultipartFile("file", "Hello, World!".getBytes());
        var userId = 1L;
        var fileName = "test.txt";

        controller.simpleUpload(file, userId, fileName);

        verify(service, times(1)).upload(userId, file, fileName);
    }

    @Test
    public void testFindWhenIdIsValidThenReturnResponseEntity() {
        Long id = 1L;
        var file = new AmazonFile();
        file.setId(id);
        file.setFileName("test.txt");
        file.setUploadedOn(LocalDateTime.now());

        when(service.findById(id)).thenReturn(file);

        var response = controller.find(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(file, response.getBody());
    }

    @Test
    public void testOnConnectionWhenValidFileCommandDTOProvidedThenServiceFindByNameAndMessagingTemplateConvertAndSendAreCalledWithCorrectParameters() {
        var dto = new FileCommandDTO("command", "test.txt", 1L);
        var file = new AmazonFile();
        file.setFileName("test.txt");

        when(service.findByName(dto.fileName())).thenReturn(file);

        controller.onConnection(dto);

        verify(service, times(1)).findByName(dto.fileName());
        verify(messagingTemplate, times(1)).convertAndSend("/topic/fileInfo/" + dto.userId(), file);
    }


    @Test
    public void testDeleteWhenIdProvidedThenServiceDeleteCalledWithSameId() {
        Long id = 1L;

        controller.delete(id);

        verify(service, times(1)).delete(id);
    }

    @Test
    public void testDeleteWhenServiceDeleteThrowsExceptionThenExceptionHandledCorrectly() {
        Long id = 1L;

        doThrow(new RuntimeException()).when(service).delete(id);

        try {
            controller.delete(id);
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }

        verify(service, times(1)).delete(id);
    }
}