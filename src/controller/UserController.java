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
    public APIResponseTemplate<UserResponseDto> getUserByUuid(String uuid){
//        return userService.getUserByUuid(uuid);
        return APIResponseTemplate.<UserResponseDto>builder()
                .status(200)
                .message("User by UUID found")
                .timeStamp(LocalDate.now())
                .data(userService.getUserByUuid(uuid))
                .build();
    }
    public APIResponseTemplate<UserResponseDto> updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto){
        return APIResponseTemplate.<UserResponseDto>builder()
                .status(200)
                .message("User updated successfully")
                .timeStamp(LocalDate.now())
                .data(userService.updateUserByUuid(uuid,updateRequestDto))
                .build();
        //        return userService.updateUserByUuid(uuid,updateRequestDto);
    }
    public APIResponseTemplate<List<UserResponseDto>> searchByName(String name){
        return APIResponseTemplate.<List<UserResponseDto>>builder()
                .status(200)
                .message("Search completed")
                .timeStamp(LocalDate.now())
                .data(userService.searchUserByName(name))
                .build();
//        return userService.searchUserByName(name);
    }
    public APIResponseTemplate<UserResponseDto> deleteUserByUuid(String uuid){
        return APIResponseTemplate.<UserResponseDto>builder()
                .status(200)
                .message("User deleted successfully")
                .timeStamp(LocalDate.now())
                .data(userService.deleteUserByUuid(uuid))
                .build();
//        return userService.deleteUserByUuid(uuid);
    };

}
