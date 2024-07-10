package com.revature.Controllers;

import com.revature.Model.Account;
import com.revature.Model.User;
import com.revature.Services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserController {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountController accountController;

    private User loggedInUser;

    public UserController(Scanner scanner, UserService userService, AccountController accountController){
        this.scanner = scanner;
        this.userService = userService;
        this.accountController = accountController;
    }

    public void promptUserForLoginService(Map<String, String> controlMap){
        System.out.print("\nWhat would you like to do?\n1. Register an account.\n2. Login.\nq. Quit.\n?: ");

        String userAction = scanner.nextLine();
        switch (userAction.strip().toLowerCase()){
            case "1":
            case "r":
            case "register":
                registerNewUser();
                break;
            case "2":
            case "l":
            case "login":
                User loggedInUser = loginUser();
                if(loggedInUser != null){
                    this.loggedInUser = loggedInUser;
                }
                break;
            case "q":
            case "quit":
                System.out.println("Goodbye!");
                controlMap.put("Program Looping", "False");
                break;
        }
    }

    public void promptUserForBankingServices(Map<String, String> controlMap){
        System.out.print("\nWelcome " + loggedInUser.getUsername() + " to the Revature Banking App!\nWhat would you like to do?\n1. Create new banking account.\n2. View all your banking accounts.\n3. Log out.\nq. Log out and quit.\n?: ");

        String userAction = scanner.nextLine();
        switch (userAction.strip().toLowerCase()) {
            case "1":
            case "c":
            case "create":

                break;
            case "2":
            case "v":
            case "view":
                accountController.viewAllUserAccounts(loggedInUser);
                break;
            case "3":
            case "log out":
            case "logout":
                logout();
                break;
            case "q":
            case "quit":
                System.out.println("Goodbye!");
                logout();
                controlMap.put("Program Looping", "False");
                break;
        }
    }

    public void logout(){
        // instead of setting it to null, replace it with an empty user
        loggedInUser = new User();

    }

    private void registerNewUser(){
        User newUser;
        do{
            System.out.println("\nEnter your new user credentials:");
            newUser = userService.validateNewCredentials(promptUserForCredentials());

            if(newUser == null){
                System.out.print("Invalid username or password\nWould you like to return to main menu?\n(y/n): ");
                String userInput = scanner.nextLine();
                switch (userInput.strip().toLowerCase()){
                    case "y":
                    case "yes":
                    case "exit":
                        return;
                }
            }

        } while(newUser == null);

        System.out.println("User " + newUser.getUsername() + " successfully created!");
    }

    private User loginUser(){
        User newUser;

        do{
            System.out.println("\nEnter your login credentials:");
            newUser = userService.checkLoginCredentials(promptUserForCredentials());

            if(newUser == null) {
                System.out.print("Invalid login information\nWould you like to return to main menu?\n(y/n): ");
                String userInput = scanner.nextLine();
                switch (userInput.strip().toLowerCase()){
                    case "y":
                    case "yes":
                    case "exit":
                        return null;
                }
            }

        }while (newUser == null);

        return newUser;

    }

    private User promptUserForCredentials(){
        String newUsername;
        String newPassword;

        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        return new User(newUsername, newPassword);

    }

    public boolean isAUserLoggedIn(){
        if (loggedInUser == null || loggedInUser.getUser_id() == 0){
            return false;
        }
        return true;
    }
}
