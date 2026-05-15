package model.dao;

import model.UserDataBase;
import model.entity.User;

import java.util.List;

public class UserDao {
    public List<User> findAll(){
        return UserDataBase.users;
    }
    public int remove(User user){
        boolean removed = UserDataBase.users.remove(user);
        return removed ? 1:0;
    }
    public User update(User uu){
        User user = UserDataBase.users.stream()
                .filter(u->u.getId().equals(uu.getId()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("User is not found"));
        // remove old version of User
        UserDataBase.users.remove(user);
        //update
        user.setName(uu.getName());
        user.setEmail(uu.getEmail());
        user.setPassword(uu.getPassword());
        user.setProfile(uu.getProfile());
//        UserDataBase.users.add(user);
        return user;
    }
    public User save(User user){
        UserDataBase.users.add(user);
        return user;
    }
}
