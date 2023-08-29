package danekerscode.technicaltask.service;

import danekerscode.technicaltask.model.AmazonFile;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonFileService {
    void delete(Long id);

    AmazonFile findById(Long id);

    AmazonFile findByName(String fileName);

    AmazonFile upload(Long userId, MultipartFile file,String fileName);
}
