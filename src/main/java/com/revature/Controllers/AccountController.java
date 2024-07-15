package com.revature.Controllers;

import com.revature.Model.Account;
import com.revature.Model.User;
import com.revature.Services.AccountService;
import com.revature.exception.UserSQLException;

import java.util.List;
import java.util.Scanner;

public class AccountController {

    private final Scanner scanner;
    private final AccountService accountService;

    public AccountController(AccountService AS, Scanner scanner){
        this.accountService = AS;
        this.scanner = scanner;
    }

    public List<Account> getAllAccountsByUserID(int userID){
        return accountService.getAllAccountsByUserID(userID);
    }

    public void viewAllUserAccounts(User loggedInUser){
        do{
            List<Account> accounts = getAllAccountsByUserID(loggedInUser.getUser_id());

            if(accounts.isEmpty()){
                System.out.println("\nThis user has no bank accounts.");
                return;
            }

            printAccounts(accounts);

            System.out.print("\nEnter the number for the account you would like to work with.\nOr type 'exit' to return to menu\n?: ");

            String userIn = scanner.nextLine();

            // We are expecting one of two input types, a string to return or a number to access
            switch (userIn.strip().toLowerCase()){
                case "exit":
                case "e":
                case "menu":
                case "return":
                    return;
            }
            // Now check if we've got a number
            try{
                int userInteger = Integer.parseInt(userIn.strip());

                boolean isValidAccountNumber = false;
                Account accountToWorkWith = null;
                for(Account a : accounts){
                    if(a.getAccount_id() == userInteger){
                        isValidAccountNumber = true;
                        accountToWorkWith = a;
                        break;
                    }
                }
                // Throw exception if this is a bad input to start the process over
                if(isValidAccountNumber == false){
                    throw new UserSQLException("Bad input");
                }

                makeChangesToAccount(loggedInUser, accountToWorkWith);

            } catch (NumberFormatException | UserSQLException e){
                System.out.println(e.getMessage());
            }

        }while (true);
    }

    public void openANewAccount(User loggedInUser){
        try{
            // Get what kind of account we are going to create
            System.out.print("\nWhat kind of account would you like to create?\ns. Savings\nc. Checking\n?: ");
            String accountType = scanner.nextLine();
            // Ensure the user properly input an account type
            switch (accountType.strip().toLowerCase()){
                case "s":
                case "sa":
                case "sav":
                case "savi":
                case "savin":
                case "saving":
                case "savings":
                    accountType = "Savings";
                    break;
                case "c":
                case "ch":
                case "che":
                case "chec":
                case "check":
                case "checki":
                case "checkin":
                case "checking":
                    accountType = "Checking";
                    break;
                default:
                    throw new UserSQLException("as " + accountType + " is not an account type.");
            }

            // Get a nickname for the account
            System.out.print("\nWould you like to enter a nickname for the account?\nIf not, you may just press enter.\n?: ");
            String accountNickname = scanner.nextLine();
            if(accountNickname.isBlank()){
                accountNickname = null;
            }

            // Get the initial balance of the account
            System.out.print("\nWhat would you like the initial balance to be?\nEntering nothing will default to zero.\n?: ");
            String accountInitialBal = scanner.nextLine();
            // Convert the balance to a float
            float initialBal;
            if(accountInitialBal.isBlank()){
                initialBal = 0;
            }
            else{
                initialBal = Float.parseFloat(accountInitialBal);
                if(initialBal < 0){
                    throw new UserSQLException("as initial balance cannot be lower than 0.");
                }
            }

            // Now put it all together
            if(accountService.openAccount(loggedInUser, accountType, accountNickname, initialBal) == 1){
                System.out.println("New banking account successfully created!");
            }

        } catch (UserSQLException | NumberFormatException e){
            System.out.println("Input not accepted " + e.getMessage());
        }
    }

    private void printAccount(Account a){
        if(a.getAccount_nickname() == null){
            System.out.println(a.getAccount_type() + " account #" + a.getAccount_id() + " has the balance of: $" + a.getAccount_balance());
        }
        else {
            System.out.println(a.getAccount_type() + " account #" + a.getAccount_id() + " '" + a.getAccount_nickname() + "'" + " has the balance of: $" + a.getAccount_balance());
        }
    }
    private void printAccounts(List<Account> accounts){
        System.out.println();

        int i = 1;
        for(Account a : accounts){
            printAccount(a);
            i++;
        }
    }

    private void makeChangesToAccount(User loggedInUser, Account account){
        do{
            System.out.println("\nMaking changes to account:");
            printAccount(account);

            System.out.print("What would you like to do with this account?\n1. Deposit\n2. Withdraw\n3. Close account\nr. return to accounts\n?: ");
            String userIn = scanner.nextLine();

            switch(userIn.strip().toLowerCase()){
                case "return":
                case "accounts":
                case "exit":
                case "r":
                    return;
                case "1":
                case "deposit":
                    attemptDeposit(account);
                    return;
                case "2":
                case "withdraw":
                    attemptWithdraw(account);
                    return;
                case "3":
                case "close":
                    if(attemptAccountClosure(loggedInUser, account)){
                        return;
                    }
                    break;
            }

        }while(true);
    }

    private void attemptWithdraw(Account account){
        try {
            System.out.print("\nHow much would you like to withdraw from this account?\n?: ");
            float userIn = Float.parseFloat(scanner.nextLine());
            int result = accountService.attemptWithdraw(account, userIn);
            if(result == 1){
                System.out.println("\n$" + userIn + " successfully withdrawn from account #" + account.getAccount_id());
            }
        } catch (NumberFormatException e){
            System.out.println("Bad input");
        }
    }

    private void attemptDeposit(Account account){
        try{
            System.out.print("\nHow much would you like to deposit to this account?\n?: ");
            float userIn = Float.parseFloat(scanner.nextLine());
            int result = accountService.attemptDeposit(account, userIn);
            if(result == 1){
                System.out.println("\n$" + userIn + " successfully withdrawn from account #" + account.getAccount_id());
            }
        } catch (NumberFormatException e){
            System.out.println("Bad input");
        }
    }

    private boolean attemptAccountClosure(User loggedInUser, Account account){
        System.out.print("\nAre you sure we wish to delete this account?\nType 'DELETE' to confirm, this action cannot be undone and all money in the account will be lost.\n?: ");
        String userIn = scanner.nextLine();

        if(userIn.strip().equals("DELETE")){
            int rowsAffected = accountService.attemptAccountClosure(loggedInUser, account);
            if(rowsAffected > 1){
                throw new RuntimeException("Multiple accounts deleted.");
            }
            else if(rowsAffected == 1){
                System.out.println("Account successfully deleted.");
                return true;
            }
            else{
                System.out.println("No such account.");
            }
        }
        return false;
    }



}
