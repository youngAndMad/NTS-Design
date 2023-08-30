package danekerscode.technicaltask.repository;

import danekerscode.technicaltask.model.AmazonFile;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
public class AmazonFileRepositoryTest {

    @Autowired
    private AmazonFileRepository amazonFileRepository;

    @Test
    public void findByNameShouldReturnNonEmptyOptional() {
        var amazonFile = new AmazonFile();
        amazonFile.setFileName("testFile");

        amazonFileRepository.save(amazonFile);

        var res = amazonFileRepository.findAmazonFileByFileName(amazonFile.getFileName());

        assertTrue(res.isPresent());
        assertEquals(res.get().getFileName() , amazonFile.getFileName());

    }

    @Test
    public void findByNameShouldReturnEmptyOptional() {
        var fileName = "hello";

        var res =amazonFileRepository.findAmazonFileByFileName(fileName);

        assertTrue(res.isEmpty());
    }
}