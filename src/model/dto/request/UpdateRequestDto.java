package model.dto.request;

public record UpdateRequestDto(
        String name,
        String email,
        String password,
        String profile
){

}
