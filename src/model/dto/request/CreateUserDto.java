package model.dto.request;

public record CreateUserDto(
        String username,
        String password,
        String email
) {
}
