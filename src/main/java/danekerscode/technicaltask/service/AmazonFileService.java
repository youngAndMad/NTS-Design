package danekerscode.technicaltask.service;

import danekerscode.technicaltask.model.AmazonFile;

public interface AmazonFileService {
    void delete(Long id);

    AmazonFile findById(Long id);

    AmazonFile findByName(String fileName);
}
