package danekerscode.technicaltask.service.impl;

import danekerscode.technicaltask.exception.EntityNotFoundException;
import danekerscode.technicaltask.mapper.AmazonFileMapper;
import danekerscode.technicaltask.model.AmazonFile;
import danekerscode.technicaltask.repository.AmazonFileRepository;
import danekerscode.technicaltask.service.AmazonFileService;
import danekerscode.technicaltask.service.S3Service;
import danekerscode.technicaltask.service.UserService;
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
    private final UserService userService;
    private final AmazonFileMapper fileMapper;

    @Value("${spring.cloud.aws.bucket.default.name}")
    private String defaultBucket;

    @Override
    public void delete(Long id) {
        var file = findById(id);

        s3Service.delete(defaultBucket, "%d/%s".formatted(file.getOwner().getId(), file.getFileName()));

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
    public AmazonFile upload(Long userId, MultipartFile file, String fileName) {
        var owner = userService.findById(userId);
        var date = convertToDateTime(fileName);

        var model = amazonFileRepository.saveAndFlush(fileMapper.toModel(date.toString(), owner, date));
        model.setFilePath("http://localhost:8888/api/v1/download?id=" + model.getId());


        s3Service.upload(defaultBucket, "%d/%s".formatted(owner.getId(), date.toString()), file);
        return amazonFileRepository.save(model);
    }

    @Override
    public byte[] download(Long id) {
        var file = findById(id);
        var content = s3Service.download(defaultBucket, "%d/%s".formatted(id, file.getFileName()));

        return content.join();
    }
}
