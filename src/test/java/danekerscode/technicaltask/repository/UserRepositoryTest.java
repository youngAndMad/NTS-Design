package danekerscode.technicaltask.repository;

import danekerscode.technicaltask.model.User;
import danekerscode.technicaltask.tc.PostgresTestContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresTestContainer.PostgresInitializer.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void afterEach() {
        this.userRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenUserEmailDoesExists() {

        var user = new User();
        user.setId(1L);
        user.setEmail("markus@gmail.com");
        user.setPassword("password");

        userRepository.save(user);

        var optional = userRepository.findUserByEmail(user.getEmail());

        assertThat(optional.isPresent()).isTrue();
    }

    @Test
    void itShouldCheckWhenUserEmailDoesNotExists() {
        String email = "hello@gmail.com";

        var expected = userRepository.findUserByEmail(email);

        assertTrue(expected.isEmpty());
    }

}