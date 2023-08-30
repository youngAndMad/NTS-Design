package danekerscode.technicaltask.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class S3ServiceTest {

    @Mock
    private S3Service s3Service;

    @Mock
    private MultipartFile multipartFile;


    @Test
    public void uploadWhenCalledWithParametersThenVerifyInvocation() {
        var bucket = "testBucket";
        var key = "testKey";

        doNothing().when(s3Service).upload(bucket, key, multipartFile);

        s3Service.upload(bucket, key, multipartFile);

        verify(s3Service, times(1)).upload(bucket, key, multipartFile);
    }

    @Test
    public void deleteWhenCalledThenVerifyInvocation() {
        var bucket = "testBucket";
        var key = "testKey";

        doNothing().when(s3Service).delete(bucket, key);

        s3Service.delete(bucket, key);

        verify(s3Service, times(1)).delete(bucket, key);
    }

    @Test
    public void deleteWhenParametersAreNullOrEmptyThenHandleGracefully() {
        var emptyBucket = "";
        String nullKey = null;

        doNothing().when(s3Service).delete(emptyBucket, nullKey);

        s3Service.delete(emptyBucket, nullKey);

        verify(s3Service, times(1)).delete(emptyBucket, nullKey);
    }

    @Test
    public void downloadWhenBucketAndKeyValidThenReturnExpectedByteArray() throws ExecutionException, InterruptedException {
        var bucket = "testBucket";
        var key = "testKey";
        var expectedBytes = new byte[]{1, 2, 3};

        when(s3Service.download(bucket, key)).thenReturn(CompletableFuture.completedFuture(expectedBytes));

        var future = s3Service.download(bucket, key);
        var actualBytes = future.get();

        assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    public void downloadWhenBucketOrKeyInvalidThenReturnExceptionallyCompletedFuture() {
        var invalidBucket = "invalidBucket";
        var invalidKey = "invalidKey";

        when(s3Service.download(invalidBucket, invalidKey)).thenReturn(CompletableFuture.failedFuture(new RuntimeException()));

        CompletableFuture<byte[]> future = s3Service.download(invalidBucket, invalidKey);

        assertTrue(future.isCompletedExceptionally());
    }

    @Test
    public void downloadWhenCalledThenBucketAndKeyAreCorrect() {
        var bucket = "testBucket";
        var key = "testKey";

        s3Service.download(bucket, key);

        verify(s3Service, times(1)).download(bucket, key);
    }
}