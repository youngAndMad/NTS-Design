package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
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

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(
            @PathVariable Long id
    ){
        userService.delete(id);
    }

}
