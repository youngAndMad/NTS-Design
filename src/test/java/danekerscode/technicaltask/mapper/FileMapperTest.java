package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.exception.FileOperationException;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileMapperTest {

    @Test
    public void convertMultipartFileToFileShouldBeValid() throws IOException {
        var mockFile = mock(MultipartFile.class);
        when(mockFile.getName()).thenReturn("testFile");
        when(mockFile.getBytes()).thenReturn(new byte[0]);

        var result = FileMapper.convertMultipartFileToFile(mockFile);

        assertNotNull(result);
        assertEquals("testFile", result.getName());
    }

    @Test
    public void convertMultipartFileToFileShouldBeFailureWithNullParam() {
        assertThrows(FileOperationException.class, () -> FileMapper.convertMultipartFileToFile(null));
    }

    @Test
    public void convertMultipartFileToFileWhenFileIsNotNullThenWritesBytesToFile() throws IOException {
        var mockFile = mock(MultipartFile.class);
        byte[] fileBytes = new byte[]{1, 2, 3};
        when(mockFile.getName()).thenReturn("testFile");
        when(mockFile.getBytes()).thenReturn(fileBytes);

        var result = FileMapper.convertMultipartFileToFile(mockFile);

        assertNotNull(result);
        assertArrayEquals(fileBytes, Files.readAllBytes(result.toPath()));
    }

    @Test
    public void convertMultipartFileToFileWhenIOExceptionOccursThenThrowsException() throws IOException {
        var mockFile = mock(MultipartFile.class);
        when(mockFile.getName()).thenReturn("testFile");
        when(mockFile.getBytes()).thenThrow(new IOException("Test exception"));

        assertThrows(FileOperationException.class, () -> FileMapper.convertMultipartFileToFile(mockFile));
    }
}