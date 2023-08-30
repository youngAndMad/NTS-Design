package danekerscode.technicaltask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Design Pattern 'Data Transfer Object'
 * <img src ="https://miro.medium.com/v2/resize:fit:828/format:webp/1*nPbU1RBOyrRxHXbtOy1NjQ.png" height="1000px"><br>
 */
public record UserDTO(
        @Email String email,
        @Size(min = 8, message = "password should be longest than 8 symbols") String password
) {
}
