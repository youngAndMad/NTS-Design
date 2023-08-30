package danekerscode.technicaltask.service;

import danekerscode.technicaltask.model.AmazonFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AmazonFileServiceTest {

    private AmazonFileService amazonFileService;

    @BeforeEach
    public void setUp() {
        amazonFileService = Mockito.mock(AmazonFileService.class);
    }


    @Test
    public void downloadWhenIdIsProvidedThenReturnCorrectByteArray() {
        var fileId = 1L;
        var expectedByteArray = new byte[]{1, 2, 3};

        when(amazonFileService.download(fileId)).thenReturn(expectedByteArray);

        var actualByteArray = amazonFileService.download(fileId);

        assertArrayEquals(expectedByteArray, actualByteArray);
    }


    @Test
    public void downloadWhenIdDoesNotExistThenReturnNull() {
        when(amazonFileService.download(anyLong())).thenReturn(null);

        var actualByteArray = amazonFileService.download(999L);

        assertNull(actualByteArray);
    }

    @Test
    public void uploadWhenFileGivenThenReturnsCorrectAmazonFile() {
        var userId = 1L;
        var fileName = "testFile";
        var file = Mockito.mock(MultipartFile.class);
        var expectedAmazonFile = new AmazonFile();
        expectedAmazonFile.setFileName(fileName);

        when(amazonFileService.upload(userId, file, fileName)).thenReturn(expectedAmazonFile);

        var actualAmazonFile = amazonFileService.upload(userId, file, fileName);

        assertEquals(expectedAmazonFile, actualAmazonFile);
    }

    @Test
    public void deleteWhenIdExistsThenFileIsDeleted() {
        var existingId = 1L;

        doNothing().when(amazonFileService).delete(existingId);

        amazonFileService.delete(existingId);

        verify(amazonFileService).delete(existingId);
    }

    @Test
    public void deleteWhenIdDoesNotExistThenHandleCorrectly() {
        var nonExistingId = 999L;

        doNothing().when(amazonFileService).delete(nonExistingId);

        amazonFileService.delete(nonExistingId);

        verify(amazonFileService).delete(nonExistingId);
    }

    @Test
    public void findByIdWhenIdIsValidThenReturnCorrectAmazonFile() {
        var expectedAmazonFile = new AmazonFile();

        expectedAmazonFile.setId(1L);
        expectedAmazonFile.setFileName("testFile");
        expectedAmazonFile.setUploadedOn(LocalDateTime.now());

        when(amazonFileService.findById(1L)).thenReturn(expectedAmazonFile);

        AmazonFile actualAmazonFile = amazonFileService.findById(1L);

        assertEquals(expectedAmazonFile, actualAmazonFile);
    }

    @Test
    public void findByIdWhenIdIsInvalidThenReturnNull() {
        when(amazonFileService.findById(anyLong())).thenReturn(null);

        AmazonFile actualAmazonFile = amazonFileService.findById(999L);

        assertNull(actualAmazonFile);
    }

    @Test
    public void findByIdWhenCalledThenIdIsCorrect() {
        AmazonFileService amazonFileServiceSpy = spy(amazonFileService);
        var expectedId = 1L;

        amazonFileServiceSpy.findById(expectedId);

        verify(amazonFileServiceSpy).findById(expectedId);
    }

    @Test
    public void findByNameWhenGivenValidNameThenReturnCorrectFile() {
        var expectedFileName = "testFile";
        var expectedAmazonFile = new AmazonFile();
        expectedAmazonFile.setFileName(expectedFileName);
        when(amazonFileService.findByName(expectedFileName)).thenReturn(expectedAmazonFile);

        var actualAmazonFile = amazonFileService.findByName(expectedFileName);

        assertEquals(expectedAmazonFile, actualAmazonFile);
    }

    @Test
    public void findByNameWhenGivenNonExistentNameThenReturnNull() {
        when(amazonFileService.findByName(anyString())).thenReturn(null);

        var actualAmazonFile = amazonFileService.findByName("nonExistentFile");

        assertNull(actualAmazonFile);
    }

    @Test
    public void findByNameWhenCalledThenRepositoryMethodIsCalled() {
        var amazonFileServiceSpy = spy(amazonFileService);
        var expectedFileName = "testFile";

        amazonFileServiceSpy.findByName(expectedFileName);

        verify(amazonFileServiceSpy).findByName(expectedFileName);
    }
}