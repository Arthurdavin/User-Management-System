package view;

import controller.UserController;
import model.dto.request.CreateUserDto;
import model.dto.request.UpdateRequestDto;
import model.dto.response.UserResponseDto;
import util.APIResponseTemplate;
import util.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class UserView {
    private final UserController userController = new UserController();

    public void showMenu() {
        while (true) {
            // Use the fancy table menu instead of simple println
            UserTableView.menu();

            int choice = InputUtil.getInt("Select an option: ");

            switch (choice) {
                case 1 -> createUser();
                case 2 -> viewAll();
                case 3 -> deleteUser();
                case 4 -> updateUser();
                case 5 -> searchByName();
                case 6 -> findByUuid();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void createUser() {
        String name = InputUtil.getString("Enter Name: ");
        String email = InputUtil.getString("Enter Email: ");
        String pass = InputUtil.getString("Enter Password: ");

//        UserResponseDto responseDto = userController.createUser(new CreateUserDto(name, email, pass)).data();
//        System.out.println("Create successfully!");
//        UserTableView.display(List.of(responseDto));
        var response = userController.createUser(new CreateUserDto(name, email, pass));
        UserTableView.display(new APIResponseTemplate<>(
                response.status(),
                response.message(),
                response.timeStamp(),
                List.of(response.data())
        ));
    }

    private void viewAll() {
//        List<UserResponseDto> users = userController.getAllUsers().data();
//        UserTableView.display(users);
        // The display logic handles the "empty" case inside UserTableView
        UserTableView.display(userController.getAllUsers());
    }

    private void findByUuid() {
        String uuid = InputUtil.getString("Enter UUID: ");
        UserResponseDto user = userController.getUserByUuid(uuid);
        if (user != null) {
            UserTableView.display(new APIResponseTemplate<>(
                    200,
                    "User find",
                    LocalDate.now(),
                    List.of(user)));
        } else {
            System.out.println("User not Found!");
        }
    }

    private void updateUser() {
        String uuid = InputUtil.getString("Enter UUID of user to update: ");
        String name = InputUtil.getString("New Name: ");
        String email = InputUtil.getString("New Email: ");
        String pass = InputUtil.getString("New Password: ");
        String profile = InputUtil.getString("New Profile URL: ");

        try {
            UserResponseDto updated = userController.updateUserByUuid(uuid, new UpdateRequestDto(name, email, pass, profile));
//            System.out.println("Updated successfully!");
            UserTableView.display(new APIResponseTemplate<>(
                    200,
                    "update successfully",
                    LocalDate.now(),
                    List.of(updated)
            ));
//            UserTableView.display(List.of(updated));
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void searchByName() {
        String name = InputUtil.getString("Search name: ");
        List<UserResponseDto> results = userController.searchByName(name);
        if(results.isEmpty()){
            System.out.println("No users found matching: "+ name);
        }
        else {
//            UserTableView.display(results);
            UserTableView.display(new APIResponseTemplate<>(
                    200,
                   "This is result",
                   LocalDate.now(),
                    results
            ));
        }
    }
    private void deleteUser(){
        String uuid = InputUtil.getString("Enter UUID to delete: ");
        try{
            userController.deleteUserByUuid(uuid);
            System.out.println("Delete Successful...");
//            UserTableView.display(List.of(deleteUser));
        }catch (RuntimeException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
}