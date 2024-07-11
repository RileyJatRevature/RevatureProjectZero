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
            System.out.println("\nThis user has no bank accounts.");
            return;
        }

        // Empty print for spacing to make it look nice :-)
        System.out.println();

        int i = 1;
        for(Account a : accounts){
            if(a.getAccount_nickname() == null){
                System.out.println(i + ": " + a.getAccount_type() + " account #" + a.getAccount_id() + " has the balance of: $" + a.getAccount_balance());

            }
            else {
                System.out.println(i + ": " + a.getAccount_type() + " account #" + a.getAccount_id() + " '" + a.getAccount_nickname() + "'" + " has the balance of: $" + a.getAccount_balance());

            }
            i++;
        }
    }
}
