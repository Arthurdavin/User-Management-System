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
        return userDao
                .findAll()
                .stream()
                .filter(u->u.getUuid().contains(uuid))
                .findFirst()
                .map(userMapper::fromUserToUserResponse)
                .orElse(null)
                ;
    }

    @Override
    public UserResponseDto updateUserByUuid(String uuid, UpdateRequestDto updateRequestDto) {

        // Find the exiting user

        User existingUser = userDao.findAll()
                .stream().filter(u->u.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User not Found with UUID: "+ uuid));

        // map data from dto to Entity

        if(updateRequestDto.name()!=null && !updateRequestDto.name().isBlank()){
            existingUser.setName(updateRequestDto.name());
        }
        if (updateRequestDto.profile()!=null && !updateRequestDto.profile().isBlank()){
            existingUser.setProfile(updateRequestDto.profile());
        }
        if (updateRequestDto.email()!=null&& !updateRequestDto.email().isBlank()){
            existingUser.setEmail(updateRequestDto.email());
        }
        if (updateRequestDto.password()!=null&&!updateRequestDto.password().isBlank()){
            existingUser.setPassword(updateRequestDto.password());
        }

        // call DAO to persist the change

        User updateUser = userDao.update(existingUser);
        return userMapper.fromUserToUserResponse(updateUser);
    }

    @Override
    public List<UserResponseDto> searchUserByName(String name) {
        // Check empty input
        if (name == null || name.isBlank()){
            return List.of();
        }
        // trim() → removes spaces before/after
        String searchName = name.trim().toLowerCase();

        return userDao.findAll()
                .stream()
                .filter(u-> u.getName()!=null &&
                        u.getName().toLowerCase().contains(searchName))
                // Convert to DTO
                // .map(u -> userMapper.fromUserToUserResponse(u))
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
