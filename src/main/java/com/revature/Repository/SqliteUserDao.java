package com.revature.Repository;

import com.revature.Model.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements UserDao{
    @Override
    public User createUser(User newUserCredentials) {
        //need sql statement
        // need a connection object
        // need to return the newly generated user
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, newUserCredentials.getUsername());
            prepStatement.setString(2, newUserCredentials.getPassword());
            int result = prepStatement.executeUpdate();
            if(result == 1){
                return newUserCredentials;
            }
            throw new UserSQLException("User could not be created: please try again");
        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";

        try(Connection connection = DatabaseConnector.createConnection()){
            List<User> users = new ArrayList<>();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                User userRecord = new User();
                userRecord.setUser_id(resultSet.getInt("user_id"));
                userRecord.setUsername(resultSet.getString("username"));
                userRecord.setPassword(resultSet.getString("password"));
                users.add(userRecord);
            }
            return users;
        } catch(SQLException e){
            throw new UserSQLException(e.getMessage());

        }
    }

    public boolean checkIfUserIDIsValid(int userID){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            return preparedStatement.executeQuery().next();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }
}
