package danekerscode.technicaltask.controller;

import com.amazonaws.Response;
import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;


    @PostMapping("register")
    ResponseEntity<?> register(
            @RequestBody UserDTO dto
    ) {
        return ResponseEntity
                .status(201)
                .body(userService.saveUser(dto));
    }
}
