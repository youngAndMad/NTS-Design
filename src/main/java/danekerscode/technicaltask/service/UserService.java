package danekerscode.technicaltask.service;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.model.User;

public interface UserService {
    User saveUser(UserDTO userDTO);

    User findById(Long id);

    void delete(Long id);
}

