package com.revature.Controllers;

import com.revature.Model.Account;
import com.revature.Model.User;
import com.revature.Services.AccountService;

import java.util.List;

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService AS){
        this.accountService = AS;
    }

    public List<Account> getAllAccountsByUserID(int userID){
        return accountService.getAllAccountsByUserID(userID);
    }

    public void viewAllUserAccounts(User loggedInUser){
        List<Account> accounts = getAllAccountsByUserID(loggedInUser.getUser_id());

        if(accounts.isEmpty()){
            System.out.println("This user has no bank accounts.");
            return;
        }

        for(Account a : accounts){
            ghjgjh
        }
    }

}
