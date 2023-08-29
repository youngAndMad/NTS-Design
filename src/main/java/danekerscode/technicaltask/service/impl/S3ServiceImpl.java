package danekerscode.technicaltask.service.impl;


import com.amazonaws.SdkClientException;
import danekerscode.technicaltask.exception.FileOperationException;
import danekerscode.technicaltask.mapper.FileMapper;
import danekerscode.technicaltask.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Override
    public void delete(String bucket, String key) {
        amazonS3.deleteObject(bucket, key);
    }

    @Override
    @Async(value = "file-uploading")
    public CompletableFuture<byte[]> download(String bucket, String key) {
        var s3Object = amazonS3.getObject(bucket, key);

        try (InputStream inputStream = s3Object.getObjectContent()) {
            // Read the content of the input stream and convert it to a byte array
            byte[] data = inputStream.readAllBytes();
            return CompletableFuture.completedFuture(data);
        } catch (IOException e) {
            // Handle any exceptions that occur during the download process
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }

    }

    @Override
    public void upload(String bucket, String key, MultipartFile file) {
        var fileToUpload = FileMapper.convertMultipartFileToFile(file);
        try {
            amazonS3.putObject(bucket, key, fileToUpload);
        } catch (SdkClientException e) {
            throw new FileOperationException(e.getMessage());
        }
    }
}
