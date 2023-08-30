package danekerscode.technicaltask.service.impl;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.exception.BadCredentialsException;
import danekerscode.technicaltask.exception.EmailRegisteredException;
import danekerscode.technicaltask.exception.EntityNotFoundException;
import danekerscode.technicaltask.mapper.UserMapper;
import danekerscode.technicaltask.model.User;
import danekerscode.technicaltask.repository.UserRepository;
import danekerscode.technicaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User saveUser(UserDTO userDTO) {

        var optional = userRepository.findUserByEmail(userDTO.email());

        if (optional.isPresent()) {
            throw new EmailRegisteredException(userDTO.email());
        }

        var model = userMapper.toModel(userDTO);

        return userRepository.save(model);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(this.findById(id));
    }

    @Override
    public User login(UserDTO userDTO) {
        var optional = userRepository.findUserByEmail(userDTO.email());

        if (optional.isEmpty() || !match.test(userDTO.password(), optional.get().getPassword())) {
            throw new BadCredentialsException();
        }

        return optional.get();

    }


    private final BiPredicate<String, String> match = String::equals;


}
