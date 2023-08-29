package danekerscode.technicaltask.service.impl;


import com.amazonaws.SdkClientException;
import danekerscode.technicaltask.exception.FileOperationException;
import danekerscode.technicaltask.mapper.FileMapper;
import danekerscode.technicaltask.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Override
    public void delete(String bucket, String key) {
        amazonS3.deleteObject(bucket, key);
    }

    @Override
    public void download(String bucket, String key) {

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
