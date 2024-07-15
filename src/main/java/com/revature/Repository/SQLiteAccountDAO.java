package com.revature.Repository;

import com.revature.Model.Account;
import com.revature.Model.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteAccountDAO {

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

    public boolean validateAccountUserPair(int userID, int accountID){
        String sql = "SELECT * FROM accounts a WHERE a.account_id = ? AND a.user_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            preparedStatement.setInt(2, userID);
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

    public int deposit(Account account, float amountToWithdraw){
        String sql = "UPDATE accounts SET account_balance = ? WHERE account_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, account.getAccount_balance() + amountToWithdraw);
            preparedStatement.setInt(2, account.getAccount_id());
            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    public int withdraw(Account account, float amountToWithdraw){
        String sql = "UPDATE accounts SET account_balance = ? WHERE account_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, account.getAccount_balance() - amountToWithdraw);
            preparedStatement.setInt(2, account.getAccount_id());
            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    public int openAccount(User loggedInUser, String acc_type, String acc_nick_name, float acc_bal){
        String sql = "INSERT INTO accounts(user_id, account_type, account_nickname, account_balance) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, loggedInUser.getUser_id());
            preparedStatement.setString(2, acc_type);
            preparedStatement.setString(3, acc_nick_name);
            preparedStatement.setFloat(4, acc_bal);
            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    public int closeAccount(int userID, int accountID){
        String sql = "DELETE FROM accounts WHERE account_id = ? AND user_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            preparedStatement.setInt(2, userID);
            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }


}
