package controller;

import model.dto.request.CreateUserDto;
import model.dto.request.UpdateRequestDto;
import model.dto.response.UserResponseDto;
import model.service.UserService;
import model.service.impl.UserServiceImpl;
import util.APIResponseTemplate;

import java.time.LocalDate;
import java.util.List;

public class UserController {

    private final UserService userService = new UserServiceImpl();

//    public UserResponseDto createUser(CreateUserDto createUserDto){
//        return userService.createUser(createUserDto);
//    }

    public APIResponseTemplate<UserResponseDto> createUser(CreateUserDto createUserDto){
        UserResponseDto userResponseDto = userService.createUser(createUserDto);
        return APIResponseTemplate.<UserResponseDto>builder()
                .status(201)
                .message("User created successfully")
                .timeStamp(LocalDate.now())
                .data(userResponseDto)
                .build();
    }

//    public List<UserResponseDto> getAllUsers(){
//        return userService.getAllUsers();
//    }
public APIResponseTemplate<List<UserResponseDto>> getAllUsers(){
    return APIResponseTemplate.<List<UserResponseDto>>builder()
            .status(200)
            .message("Get all users successfully")
            .timeStamp(LocalDate.now())
            .data(userService.getAllUsers())
            .build();
}
    public UserResponseDto getUserByUuid(String uuid){
        return userService.getUserByUuid(uuid);
    }
    public UserResponseDto updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto){
        return userService.updateUserByUuid(uuid,updateRequestDto);
    }
    public List<UserResponseDto> searchByName(String name){
        return userService.searchUserByName(name);
    }
    public UserResponseDto deleteUserByUuid(String uuid){
        return userService.deleteUserByUuid(uuid);
    };

}
