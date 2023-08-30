package danekerscode.technicaltask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.model.User;
import danekerscode.technicaltask.repository.UserRepository;
import danekerscode.technicaltask.tc.PostgresTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresTestContainer.PostgresInitializer.class)
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    void shouldBeValidRegistration() throws Exception {
        var dto = new UserDTO(
                "email@gmail.com",
                "password"
        );

        var res = mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        res.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        var response = res.andReturn().getResponse().getContentAsString();
        var user = objectMapper.readValue(response, User.class);

        assertNotNull(user.getId());
        assertEquals(user.getEmail(), dto.email());
        assertNull(user.getPassword());
    }

    @Test
    void shouldBeInvalidRegistrationWithInvalidParams() throws Exception {
        var dto = new UserDTO(
                "invalid",
                "invalid"
        );

        /*
        path will import from config server
        in test context servlet.context.path=/
        */
        var res = mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        res
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var response = res.andReturn().getResponse().getContentAsString();
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);

        assertEquals("Bad Request", problemDetail.getTitle());
        assertEquals(400, problemDetail.getStatus());
        assertTrue(problemDetail.getDetail().contains("password should be longest than 8 symbols"));
        assertTrue(problemDetail.getDetail().contains("must be a well-formed email address"));
    }


    @Test
    void shouldBeInvalidRegistrationWithExistEmail() throws Exception {
        var email = "daneker@gmail.com";
        var dto = new UserDTO(
                email,
                "StrongPassword"
        );

        var user = new User();
        user.setEmail(email);
        userRepository.save(user);

        /*
        path will import from config server
        in test context servlet.context.path=/
        */
        var res = mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        res
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var response = res.andReturn().getResponse().getContentAsString();
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);

        assertEquals("Bad Request", problemDetail.getTitle());
        assertEquals(400, problemDetail.getStatus());
        assertEquals("email: daneker@gmail.com registered yet", problemDetail.getDetail());
    }


    @Test
    void shouldBeDeletedById() throws Exception {

        var user = new User();
        user.setId(3L);
        userRepository.save(user);

        var findByIdResponse = mockMvc.perform(
                delete("/user/" + user.getId())
        );

        findByIdResponse
                .andExpect(status().isNoContent());

    }

    @Test
    void deletedByIdShouldBeFailureWithNonExistUser() throws Exception {

        var user = new User();
        user.setId(1L);


        var res = mockMvc.perform(
                delete("/user/" + user.getId())
        );

        res
                .andExpect(status().isNotFound());

        var response = res.andReturn().getResponse().getContentAsString();
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);

        assertEquals("Not Found", problemDetail.getTitle());
        assertEquals(404, problemDetail.getStatus());

    }

    @Test
    void loginShouldBeSuccess() throws Exception {
        var email = "email@gmail.com";
        var password = "password";

        var user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);


        var res = mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UserDTO(
                                        email, password
                                )
                        ))
        );

        res.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        var response = res.andReturn().getResponse().getContentAsString();
        var resUser = objectMapper.readValue(response, User.class);

        assertNotNull(resUser.getId());
        assertEquals(resUser.getEmail(), email);
        assertNull(resUser.getPassword());

    }

    @Test
    void loginShouldBeFailureWithInvalidCredentials() throws Exception{
        var dto = new UserDTO(
                "invalid@gmail.com",
                "invalidPassword"
        );

        var res = mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        res.andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var response = res.andReturn().getResponse().getContentAsString();
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);

        assertEquals("Unauthorized", problemDetail.getTitle());
        assertEquals(401, problemDetail.getStatus());
        assertEquals("invalid credentials", problemDetail.getDetail());
    }


}