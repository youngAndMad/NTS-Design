package danekerscode.technicaltask.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface S3Service {
    void delete(String bucket, String key);

    CompletableFuture<byte[]> download(String bucket, String key);

    void upload(String bucket, String key, MultipartFile file);
}
