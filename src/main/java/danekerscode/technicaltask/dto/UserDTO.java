package danekerscode.technicaltask.dto;

/**
 * Design Pattern 'Data Transfer Object'
 * <img src ="https://miro.medium.com/v2/resize:fit:828/format:webp/1*nPbU1RBOyrRxHXbtOy1NjQ.png" height="1000px"><br>
 */
public record UserDTO(
        String email,
        String password
) {
}
