package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void toModelShouldBeValidWithNonNullUserParams() {
        var userDTO = new UserDTO("test@test.com", "password");

        var user = userMapper.toModel(userDTO);

        assertNotNull(user);
        assertEquals(userDTO.email(), user.getEmail());
        assertEquals(userDTO.password(), user.getPassword());
        assertNull(user.getId());
        assertTrue(user.getFiles().isEmpty());
    }

    @Test
    void toModelShouldBeFailureWithNullUserParams() {
        var userDTO = new UserDTO(null, null);

        var user = userMapper.toModel(userDTO);

        assertNotNull(user);
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getId());
        assertTrue(user.getFiles().isEmpty());
    }
}