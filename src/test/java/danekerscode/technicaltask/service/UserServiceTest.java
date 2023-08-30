package danekerscode.technicaltask.service;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.exception.BadCredentialsException;
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
    void loginWhenCredentialsAreValidThenReturnUser() {
        var userDTO = new UserDTO("valid@example.com", "Valid User");
        var user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        when(userRepository.findUserByEmail(userDTO.email())).thenReturn(Optional.of(user));

        var result = userService.login(userDTO);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    void loginWhenCredentialsAreInvalidThenThrowException() {
        var userDTO = new UserDTO("invalid@example.com", "Invalid User");

        when(userRepository.findUserByEmail(userDTO.email())).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> userService.login(userDTO));
    }


    @Test
    void saveUserShouldBeValid() {
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
    void saveUserShouldBeThrowExceptionDuplicateEmail() {
        var existingUserDTO = new UserDTO("existing@example.com", "Existing User");

        when(userRepository.findUserByEmail(existingUserDTO.email())).thenReturn(Optional.of(new User()));

        assertThrows(EmailRegisteredException.class, () -> userService.saveUser(existingUserDTO));
    }

    @Test
    void findByIdShouldReturnUser() {
        var userId = 1L;
        var user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void findByIdShouldBeThrowException() {
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
