package danekerscode.technicaltask.service.impl;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.exception.EntityNotFoundException;
import danekerscode.technicaltask.mapper.UserMapper;
import danekerscode.technicaltask.model.User;
import danekerscode.technicaltask.repository.UserRepository;
import danekerscode.technicaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User saveUser(UserDTO userDTO) {
        var model = userMapper.toModel(userDTO);

        return userRepository.save(model);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }
}
