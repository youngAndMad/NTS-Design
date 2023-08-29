package danekerscode.technicaltask.repository;

import danekerscode.technicaltask.model.AmazonFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmazonFileRepository
        extends JpaRepository<AmazonFile, Long> {

    Optional<AmazonFile> findAmazonFileByFileName(String fileName);
}