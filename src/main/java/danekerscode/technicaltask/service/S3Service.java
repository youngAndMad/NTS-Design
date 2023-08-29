package danekerscode.technicaltask.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    void delete(String bucket, String key);

    void download(String bucket, String key);

    void upload(String bucket, String key, MultipartFile file);
}
