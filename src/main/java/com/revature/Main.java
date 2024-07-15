package com.revature;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import com.revature.Controllers.AccountController;
import com.revature.Controllers.UserController;
import com.revature.Repository.SQLiteAccountDAO;
import com.revature.Repository.SqliteUserDao;
import com.revature.Repository.UserDao;
import com.revature.Services.AccountService;
import com.revature.Services.UserService;
import com.revature.exception.LoginFail;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map<String, String> controlMap = new HashMap<>();
        controlMap.put("Program Looping", "True");

        try (Scanner scanner = new Scanner(System.in)) {

            SQLiteAccountDAO accountDAO = new SQLiteAccountDAO();
            AccountService accountService = new AccountService(accountDAO);
            AccountController accountController = new AccountController(accountService, scanner);

            UserDao userDao = new SqliteUserDao();
            UserService userService = new UserService(userDao);
            UserController userController = new UserController(scanner, userService, accountController);

            while (Boolean.parseBoolean(controlMap.get("Program Looping"))) {
                if (userController.isAUserLoggedIn()) {
                    userController.promptUserForBankingServices(controlMap);
                } else {
                    userController.promptUserForLoginService(controlMap);
                }
            }
        } catch(LoginFail exception){
            System.out.println(exception.getMessage());
        }
    }
}
