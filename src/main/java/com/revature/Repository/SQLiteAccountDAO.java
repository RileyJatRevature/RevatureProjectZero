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

    private UserDao userDao;

    public SQLiteAccountDAO(UserDao userDao){
        this.userDao = userDao;
    }


    public boolean validateAccountUserPair(int userID, int accountID){
        String sql = "SELECT uaj.account_id, uaj.user_id, a.account_type, a.account_nickname, a.account_balance FROM user_account_join uaj " +
                "JOIN users u ON uaj.user_id = u.user_id " +
                "JOIN accounts a on uaj.account_id = a.account_id " +
                "WHERE u.user_id = ? AND a.account_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, accountID);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    public List<Account> getAllAccountsByUserID(int userID){
        String sql = "SELECT uaj.account_id, uaj.user_id, a.account_type, a.account_nickname, a.account_balance FROM user_account_join uaj JOIN users u ON uaj.user_id = u.user_id JOIN accounts a on uaj.account_id = a.account_id WHERE u.user_id = ?; ";
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

        if(attemptOpenAccount(acc_type, acc_nick_name, acc_bal) == 0){
            return 0;
        }

        Account newAccount = this.getNewestAccount();
        assert newAccount != null;

        return pairUserAndAccount(loggedInUser, newAccount);
    }

    private int pairUserAndAccount(User user, Account account){
        String sql = "INSERT INTO user_account_join(user_id, account_id) VALUES (?, ?)";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUser_id());
            preparedStatement.setInt(2, account.getAccount_id());
            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }

    }

    private int attemptOpenAccount(String acc_type, String acc_nick_name, float acc_bal){
        String sql = "INSERT INTO accounts(account_type, account_nickname, account_balance) VALUES (?, ?, ?)";
        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc_type);
            preparedStatement.setString(2, acc_nick_name);
            preparedStatement.setFloat(3, acc_bal);

            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    private Account getNewestAccount(){
        String sql = "SELECT * FROM accounts ORDER BY account_id DESC LIMIT 1";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Account account = new Account();

                account.setAccount_id(resultSet.getInt("account_id"));
                account.setAccount_type(resultSet.getString("account_type"));
                account.setAccount_nickname(resultSet.getString("account_nickname"));
                account.setAccount_balance(resultSet.getFloat("account_balance"));

                return account;
            }
            return null;
        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }

    public int closeAccount(int accountID){
        String sql = "DELETE FROM accounts WHERE account_id = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            return preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new UserSQLException(e.getMessage());
        }
    }


}
