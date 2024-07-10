package com.revature.Model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private int user_id;
    private String username;
    private String password;


    public User(){}

    public User(String name, String pass){
        this.username = name;
        this.password = pass;

    }

    public User(int user_id, String name, String pass){
        this.user_id = user_id;
        this.username = name;
        this.password = pass;

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                '}';
    }
}
