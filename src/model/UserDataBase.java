package model;

import model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDataBase {
    public static List<User> users = new ArrayList<>(

    );

    // Static block to initialize default data
    static {
        users.add(new User(
                1,
                UUID.randomUUID().toString(),
                "admin",
                "admin@gmail.com",
                "admin123",
                "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
        ));

        users.add(new User(
                2,
                UUID.randomUUID().toString(),
                "Davin",
                "davin@gmail.com",
                "Davin123",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy3ueXVQSJZxzx0sSm-zGrZt_kQugr5O4acw&s"
        ));

        users.add(new User(
                3,
                UUID.randomUUID().toString(),
                "sophia",
                "sophia@gmail.com",
                "sophia123",
                "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
        ));
    }
}