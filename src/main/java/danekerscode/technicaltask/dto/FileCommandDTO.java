package danekerscode.technicaltask.dto;

public record FileCommandDTO(
    String command,
    String fileName,
    Long userId
) {
}
