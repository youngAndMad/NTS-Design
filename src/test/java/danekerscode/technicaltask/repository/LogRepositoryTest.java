package danekerscode.technicaltask.repository;

import danekerscode.technicaltask.model.Log;
import danekerscode.technicaltask.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LogRepositoryTest{
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        logRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findAllByOwnerIdShouldReturnListWith2Logs() {
        Long ownerId = 1L;
        var user = new User();
        user.setId(ownerId);
        userRepository.save(user);

        var log1 = new Log();
        log1.setContent("message");
        log1.setOwner(user);

        var log2 = new Log();
        log2.setContent("template");
        log2.setOwner(user);


        logRepository.saveAll(List.of(log1, log2));

        List<Log> logs = logRepository.findAllByOwnerId(ownerId);

        assertThat(logs).hasSize(2);
        assertThat(logs).containsExactlyInAnyOrder(log1, log2);
    }

    @Test
    public void testFindAllByOwnerIdShouldReturnEmptyList() {
        Long ownerId = 1L;

        List<Log> logs = logRepository.findAllByOwnerId(ownerId);

        assertThat(logs).isEmpty();
    }

}
