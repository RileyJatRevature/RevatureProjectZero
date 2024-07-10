package com.revature.Repository;

import com.revature.Model.Account;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqLiteAccountDAO {

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

    public List<Account> getAllAccountsByUserID(int userID){
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        List<Account> retAccounts = new ArrayList<>();
        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Account account = new Account();

                account.setAccount_id(resultSet.getInt("account_id"));
                account.setUser_id(resultSet.getInt("user_id"));
                account.setAccount_type(resultSet.getString("account_type"));
                account.setAccount_nickname(resultSet.getString("account_nickname"));
                account.setAccount_balance(resultSet.getFloat("account_balance"));

                retAccounts.add(account);
            }
        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
        return retAccounts;
    }
}
