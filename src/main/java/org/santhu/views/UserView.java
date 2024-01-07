package org.santhu.views;

import org.santhu.dao.UserOperationsDAO;
import org.santhu.dao.UserRegistrationDAO;
import org.santhu.db.MyConnection;
import org.santhu.model.User;
import org.santhu.model.User_Passwords;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    Scanner scanner;
    public void showScreen(){
        while (true){
            System.out.println("1. Login\n2. Register\n3. exit");
            scanner = new Scanner(System.in);
            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    MyConnection.closeConnection();
                    System.exit(0);
                }
            }
        }
    }

    private void register() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to Registration page");
        System.out.println("enter your user name : ");
        String userName = scanner.nextLine();
        System.out.println("enter your password : ");
        String password = scanner.nextLine();
        System.out.println("confirm your password : ");
        String confirmPassword = scanner.nextLine();
        while (!password.equals(confirmPassword)) {
            System.out.println("u r password does not match");
            System.out.println("enter your password : ");
            password = scanner.nextLine();
            System.out.println("confirm your password : ");
            confirmPassword = scanner.nextLine();
        }
        User user = new User(userName, password);
        try {
            if (!UserRegistrationDAO.isExists(userName)) {
                int response = UserRegistrationDAO.register(user);
                if (response != 0) {
                    System.err.println("user registered");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to Login page");
        System.out.println("enter your user name : ");
        String userName = scanner.nextLine();
        System.out.println("enter your password : ");
        String password = scanner.nextLine();
        try {
            String response = UserRegistrationDAO.isUserValid(userName, password);
            switch (response) {
                case "true" -> userLoginScreen(userName);
                case "false" -> {
                    System.out.println("incorrect password");
                    login();
                }
                case "user not found" -> System.out.println("user not registered");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void userLoginScreen(String userName) {
        while (true) {
        System.out.println("Welcome " + userName);
            System.out.println("1. show saved passwords\n2. add new password\n3. delete password\n4. exit");
            scanner = new Scanner(System.in);
            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1 -> {
                    try {
                        List<User_Passwords> passwordsList = UserOperationsDAO.getAllSavedPasswords(userName);
                        if (!passwordsList.isEmpty()) {
                            passwordsList.forEach(
                                    data -> System.out.println(
                                            data.getPass_count() + " - " + data.getUser_id() + " - " +  data.getWebsite_name() + " - " + data.getWebsite_username() + " - " + data.getWebsite_password()
                                    )
                            );
                        }else{
                            System.out.println("No save password found");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter the following details : ");
                    scanner = new Scanner(System.in);
                    System.out.println("Enter website address : ");
                    String websiteName = scanner.nextLine();
                    System.out.println("Enter website username : ");
                    String websiteUsername = scanner.nextLine();
                    System.out.println("Enter website password: ");
                    String websitePassword = scanner.nextLine();
                    try {
                        int id = UserOperationsDAO.getUserId(userName);
                        int response = UserOperationsDAO.savePassword(new User_Passwords(id, websiteName, websiteUsername, websitePassword));
                        System.out.println(id);
                        System.out.println(response);
                        if(response != 0){
                            System.out.println("password saved");
                        }else{
                            System.out.println("something went wrong");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    System.out.println("Saved Passwords");
                    try {
                        List<User_Passwords> passwordsList = UserOperationsDAO.getAllSavedPasswords(userName);
                        if (!passwordsList.isEmpty()) {
                            passwordsList.forEach(
                                    data -> System.out.println(
                                            data.getPass_count() + " - " + data.getUser_id() + " - " +  data.getWebsite_name() + " - " + data.getWebsite_username() + " - " + data.getWebsite_password()
                                    )
                            );
                        }else{
                            System.out.println("No save password found");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Enter the user id of the password that u want to delete : ");
                    scanner = new Scanner(System.in);
                    try {
                        int delete = Integer.parseInt(scanner.nextLine());
                        int response = UserOperationsDAO.deletePassword(delete);
                        if(response != 0) System.out.println("password deleted");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 4 -> {
                    return;
                }
            }
        }
    }
}
