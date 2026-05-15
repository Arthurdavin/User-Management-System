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
                case 0 -> {
                    System.out.println("System exited...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void createUser() {
        String name = InputUtil.getString("Enter Name: ");
        String email = InputUtil.getString("Enter Email: ");
        String pass = InputUtil.getString("Enter Password: ");

        var response = userController.createUser(new CreateUserDto(name, email, pass));

        displaySingle(response);

    }

    private void viewAll() {
        var response = userController.getAllUsers();
        UserTableView.display(response);
    }

    private void findByUuid() {
        String uuid = InputUtil.getString("Enter UUID: ");
        try{
            var response = userController.getUserByUuid(uuid);
            if (response.data() != null){
                displaySingle(response);
            }
            else {
                System.out.println("User not found!");
            }
        }catch (RuntimeException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }

    private void updateUser() {
        String uuid = InputUtil.getString("Enter UUID of user to update: ");
        String name = InputUtil.getString("New Name: ");
        String email = InputUtil.getString("New Email: ");
        String pass = InputUtil.getString("New Password: ");
        String profile = InputUtil.getString("New Profile URL: ");

        try {
            var response = userController.updateUserByUuid(
                    uuid,
                    new UpdateRequestDto(name, email, pass, profile)
            );

            UserTableView.display(new APIResponseTemplate<>(
                    response.status(),
                    response.message(),
                    response.timeStamp(),
                    List.of(response.data())
            ));

        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByName() {
        String name = InputUtil.getString("Search name: ");
        var response = userController.searchByName(name);
        if(response.data().isEmpty()){
            System.out.println("No users found matching: "+ name);
        }
        else {
            UserTableView.display(response);
        }
    }
    private void deleteUser(){
        String uuid = InputUtil.getString("Enter UUID to delete: ");
        try{
            var response = userController.deleteUserByUuid(uuid);
            displaySingle(response);
        }catch (RuntimeException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }

    private void displaySingle(APIResponseTemplate<UserResponseDto> response) {
        UserTableView.display(new APIResponseTemplate<>(
                response.status(),
                response.message(),
                response.timeStamp(),
                List.of(response.data())
        ));
    }

}