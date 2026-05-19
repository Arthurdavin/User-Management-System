package model.service.impl;

import mapper.UserMapper;
import model.dao.UserDao;
import model.dto.request.CreateUserDto;
import model.dto.request.UpdateRequestDto;
import model.dto.response.UserResponseDto;
import model.entity.User;
import model.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDao();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        // create user
        // map from createUserDto to User

        User user = userMapper.fromUserDaoToUser(createUserDto);

        userDao.save(user);
        // map from User to UserResponseDto
        return userMapper.fromUserToUserResponse(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userDao.findAll()
                .stream()
                .map(userMapper::fromUserToUserResponse)
                .toList()
                ;
    }

    @Override
    public UserResponseDto getUserByUuid(String uuid) {
        User user = userDao.searchByUuid(uuid);
        if(user==null){
            System.out.println("User Not Found");
        }
        return userMapper.fromUserToUserResponse(user);
    }

    @Override
    public UserResponseDto updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto) {

        User exitingUser = userDao.searchByUuid(uuid);
        if (exitingUser == null){
            throw new RuntimeException("User not found: "+ uuid);
        }
        if(updateRequestDto.name()!=null && !updateRequestDto.name().isBlank()){
            exitingUser.setName(updateRequestDto.name());
        }
        if (updateRequestDto.profile()!=null && !updateRequestDto.profile().isBlank()){
            exitingUser.setProfile(updateRequestDto.profile());
        }
        if (updateRequestDto.email()!=null&& !updateRequestDto.email().isBlank()){
            exitingUser.setEmail(updateRequestDto.email());
        }
        if (updateRequestDto.password()!=null&&!updateRequestDto.password().isBlank()){
            exitingUser.setPassword(updateRequestDto.password());
        }
        User updateUser = userDao.update(exitingUser);
        return userMapper.fromUserToUserResponse(updateUser);

    }

    @Override
    public List<UserResponseDto> searchUserByName(String name) {
        return userDao.searchByName(name)
                .stream()
                .map(userMapper::fromUserToUserResponse)
                .toList();
    }

    @Override
    public UserResponseDto deleteUserByUuid(String uuid) {
        // check user first
        User userToDelete = userDao.findAll()
                .stream().filter(u->u.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User 404: "+ uuid));

        // remove it

        userDao.remove(userToDelete);
        // Return deleted user info Convert deleted User entity to DTO.
        return userMapper.fromUserToUserResponse(userToDelete);
    }

}
