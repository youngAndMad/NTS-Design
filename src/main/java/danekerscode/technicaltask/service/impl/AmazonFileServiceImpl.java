package danekerscode.technicaltask.service.impl;

import danekerscode.technicaltask.exception.EntityNotFoundException;
import danekerscode.technicaltask.model.AmazonFile;
import danekerscode.technicaltask.repository.AmazonFileRepository;
import danekerscode.technicaltask.service.AmazonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmazonFileServiceImpl implements AmazonFileService {

    private final AmazonFileRepository amazonFileRepository;

    @Override
    public void delete(Long id) {
        amazonFileRepository.delete(
                this.findById(id)
        );
    }

    @Override
    public AmazonFile findById(Long id) {
        return amazonFileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AmazonFile.class, id));

    }

    @Override
    public AmazonFile findByName(String fileName) {
        return amazonFileRepository.findAmazonFileByFileName(fileName)
                .orElseThrow(() -> new EntityNotFoundException(AmazonFile.class));
    }
}
