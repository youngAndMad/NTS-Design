package danekerscode.technicaltask.controller;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static danekerscode.technicaltask.utils.ReturnError.validateRequest;
import static org.springframework.http.HttpStatus.*;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    ResponseEntity<?> register(
            @RequestBody @Valid UserDTO dto,
            BindingResult br
    ) {
        validateRequest(br);
        return ResponseEntity
                .status(201)
                .body(userService.saveUser(dto));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(
            @PathVariable Long id
    ) {
        userService.delete(id);
    }

    @PostMapping("login")
    ResponseEntity<?> login(
            @RequestBody @Valid UserDTO userDTO,
            BindingResult br
    ) {
        validateRequest(br);
        return ResponseEntity
                .ok(userService.login(userDTO));
    }


}
