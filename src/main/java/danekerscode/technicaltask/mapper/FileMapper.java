package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.exception.FileOperationException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@UtilityClass
public class FileMapper {

    public static File convertMultipartFileToFile(MultipartFile file) {
        if (file == null) {
            throw new FileOperationException("error during file converting");
        }

        var convertedFile = new File(file.getName());

        /*
         Block try-catch with resources
         it will be closed regardless of whether the try statement completes normally or abruptly
         because FileOutputStream implement AutoClosable
         */
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
            return convertedFile;
        } catch (IOException e) {
            throw new FileOperationException(e.getMessage());
        }

    }


}