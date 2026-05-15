package model.dto.response;

public record UserResponseDto(
        String uuid,
        String name,
        String profile,
        String email
) {

}
