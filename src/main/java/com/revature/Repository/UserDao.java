package com.revature.Repository;

import com.revature.Model.User;

import java.util.List;

public interface UserDao {

    User createUser(User newUserCredentials);

    List<User> getAllUsers();
}
