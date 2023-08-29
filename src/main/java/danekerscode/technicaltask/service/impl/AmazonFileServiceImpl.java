package danekerscode.technicaltask.service.impl;

import danekerscode.technicaltask.exception.EntityNotFoundException;
import danekerscode.technicaltask.model.AmazonFile;
import danekerscode.technicaltask.repository.AmazonFileRepository;
import danekerscode.technicaltask.service.AmazonFileService;
import danekerscode.technicaltask.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static danekerscode.technicaltask.mapper.DateMapper.convertToDateTime;

@Service
@RequiredArgsConstructor
public class AmazonFileServiceImpl implements AmazonFileService {

    private final AmazonFileRepository amazonFileRepository;
    private final S3Service s3Service;

    @Value("${spring.cloud.aws.bucket.default.name}")
    private String defaultBucket;

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

    @Override
    public AmazonFile upload(Long userId, MultipartFile file) {
        s3Service.upload(defaultBucket ," convertToDateTime(file.getName())" , file);
        return null;
    }
}
