package com.revature.Repository;

import com.revature.Model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUser implements UserDao{

    private final List<User> users;

    public InMemoryUser(){
        users = new ArrayList<>();
        users.add(new User("admin", "1234"));
    }

    @Override
    public User createUser(User newUserCredentials) {
        users.add(newUserCredentials);
        return newUserCredentials;
    }

    @Override
    public List<User> getAllUsers(){
        return  users;
    }



}
