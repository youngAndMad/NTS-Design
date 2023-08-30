package danekerscode.technicaltask.service;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.exception.EmailRegisteredException;
import danekerscode.technicaltask.exception.EntityNotFoundException;
import danekerscode.technicaltask.mapper.UserMapper;
import danekerscode.technicaltask.model.User;
import danekerscode.technicaltask.repository.UserRepository;
import danekerscode.technicaltask.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testSaveUser_NewUser() {
        var newUserDTO = new UserDTO("newuser@example.com", "New User");

        var user = new User();

        when(userRepository.findUserByEmail(newUserDTO.email())).thenReturn(Optional.empty());
        when(userMapper.toModel(newUserDTO)).thenReturn(user);
        when(userService.saveUser(newUserDTO)).thenReturn(user);

        var savedUser = userService.saveUser(newUserDTO);

        assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSaveUser_DuplicateEmail() {
        var existingUserDTO = new UserDTO("existing@example.com", "Existing User");

        when(userRepository.findUserByEmail(existingUserDTO.email())).thenReturn(Optional.of(new User()));

        assertThrows(EmailRegisteredException.class, () -> userService.saveUser(existingUserDTO));
    }

    @Test
    void testFindById_UserExists() {
        var userId = 1L;
        var user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testFindById_UserNotFound() {
        var userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    void testDeleteWhenIdIsValidThenUserIsDeleted() {
        var userId = 1L;
        var user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteWhenIdIsInvalidThenExceptionIsThrown() {
        var userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
    }

}
