package danekerscode.technicaltask.repository;

import danekerscode.technicaltask.model.AmazonFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmazonFileRepository
        extends JpaRepository<AmazonFile, Long> {
}