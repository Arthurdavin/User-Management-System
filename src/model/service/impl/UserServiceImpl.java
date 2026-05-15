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
                .map(userMapper::fromUserToUserResponse)// It transforms each User into UserResponseDto.
                .toList();
    }

    @Override
    public UserResponseDto getUserByUuid(String uuid) {
        return userDao.findAll()
                .stream()
                .filter(u->u.getUuid().equals(uuid))
                .findFirst()
                .map(userMapper::fromUserToUserResponse)
                .orElse(null);
    }

    @Override
    public UserResponseDto updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto) {
        // Find the exiting user
        User existingUser = userDao.findAll()
                .stream().filter(u->u.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User not Found with UUID: "+ uuid));

        // map data from dto to Entity
        existingUser.setProfile(updateRequestDto.profile());
        existingUser.setEmail(updateRequestDto.email());
        existingUser.setName(updateRequestDto.name());
        existingUser.setProfile(updateRequestDto.profile());

        // call DAO to persist the change

        User updateUser = userDao.update(existingUser);
        return userMapper.fromUserToUserResponse(updateUser);
    }

    @Override
    public List<UserResponseDto> searchUserByName(String name) {

        if(name == null || name.isBlank()){
            return List.of();
        }

        String searchToken = name.trim().toLowerCase();

        return userDao.findAll()
                .stream()
                .filter(u->u.getName()!=null
                        && u.getName().toLowerCase().contains(searchToken))
                .map(userMapper::fromUserToUserResponse)
                .toList();
    }

    @Override
    public UserResponseDto deleteUserByUuid(String uuid) {
        // 1. Find the user first so we can return their info after deletion
        User userToDelete = userDao.findAll()
                .stream()
                .filter(u->u.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User Not Found with: "+ uuid));
        // 2. Perform the deletion via DAO
        userDao.remove(userToDelete);
        // 3. Return the mapped DTO
        return userMapper.fromUserToUserResponse(userToDelete);
    }

}
