package com.revature.Services;

import com.revature.Model.User;
import com.revature.Repository.UserDao;
import com.revature.exception.LoginFail;

import java.util.List;

public class UserService {

    UserDao userDao;

    public UserService(UserDao dao){
        this.userDao = dao;
    }

    public User validateNewCredentials(User newUserCredentials){
        if(!checkUsernamePasswordLength(newUserCredentials)){
            return null;
        }
        if(!checkUsernameIsUnique(newUserCredentials)){
            return null;
        }

        return userDao.createUser(newUserCredentials);
    }

    private boolean checkUsernamePasswordLength(User newUserCredentials){
        int usernameLength = newUserCredentials.getUsername().length();
        int passwordLength = newUserCredentials.getPassword().length();
        boolean usernameIsValid = usernameLength <= 30 && usernameLength > 0;
        boolean passwordIsValid = passwordLength <= 30 && passwordLength > 0;

        return usernameIsValid && passwordIsValid;
    }

    private boolean checkUsernameIsUnique(User newUserCredentials){
        List<User> users = userDao.getAllUsers();
        for(User u : users){
            if (newUserCredentials.getUsername().equals(u.getUsername())){
                return false;
            }
        }

        return true;
    }

    public User checkLoginCredentials(User credentials){
        String credentialsUsername = credentials.getUsername();
        String credentialsPassword = credentials.getPassword();

        for(User user : userDao.getAllUsers()){
            boolean usernameMatches = user.getUsername().equals(credentialsUsername);
            boolean passwordMatches = user.getPassword().equals(credentialsPassword);
            if(usernameMatches && passwordMatches){
                return user;
            }
        }

        throw new LoginFail("Bad username password combination");
    }
}
