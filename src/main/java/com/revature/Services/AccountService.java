package com.revature.Services;

import com.revature.Model.Account;
import com.revature.Repository.SqLiteAccountDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final SqLiteAccountDAO accountDAO;

    public AccountService(SqLiteAccountDAO accountDAO){
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

}
