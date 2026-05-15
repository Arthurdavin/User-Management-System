package mapper;

import model.dto.request.CreateUserDto;
import model.dto.response.UserResponseDto;
import model.entity.User;

import java.util.Random;
import java.util.UUID;

public class UserMapper {
    public User fromUserDaoToUser(CreateUserDto createUserDto){
        return new User(new Random().nextInt(99999),
                UUID.randomUUID().toString(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy3ueXVQSJZxzx0sSm-zGrZt_kQugr5O4acw&s");
    }
    public UserResponseDto fromUserToUserResponse(User user){
        return new UserResponseDto(
                user.getUuid(),
                user.getName(),
                user.getProfile(),
                user.getEmail()
        );
    }
}
