package com.revature.Services;

import com.revature.Model.Account;
import com.revature.Model.User;
import com.revature.Repository.SQLiteAccountDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final SQLiteAccountDAO accountDAO;

    public AccountService(SQLiteAccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccountsByUserID(int userID){
        // Check to ensure this user ID is real
        if(accountDAO.checkIfUserIDIsValid(userID)){
            // If real call DAO method of same name
            return accountDAO.getAllAccountsByUserID(userID);
        }
        // Otherwise
        return new ArrayList<>();
    }

    public int attemptAccountClosure(User user, Account account){
        // Check to ensure account belongs to user
        if(accountDAO.validateAccountUserPair(user.getUser_id(), account.getAccount_id())){
            // Call DAO method to delete row
            return accountDAO.closeAccount(user.getUser_id(), account.getAccount_id());
        }
        return 0;
    }

    public int attemptWithdraw(Account account, float amountToWithdraw){
        if(amountToWithdraw <= account.getAccount_balance()){
            return accountDAO.withdraw(account, amountToWithdraw);
        } else {
            System.out.println("Not enough funds in account.");
        }
        return 0;
    }

    public int attemptDeposit(Account account, float amountToDeposit){
        return accountDAO.deposit(account, amountToDeposit);
    }

    public int openAccount(User loggedInUser, String acc_type, String acc_nick_name, float acc_bal){
        // Check if the user is real here
        return accountDAO.openAccount(loggedInUser, acc_type, acc_nick_name, acc_bal);
    }
}
