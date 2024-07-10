package com.revature.Model;

public class Account {

    private int account_id;
    private int user_id;
    private String account_type;
    private String account_nickname;
    private float account_balance;

    /*public Account(int acc_id, int us_id, String acc_type, String acc_nickname, float acc_bal){
        this.account_id = acc_id;
        this.user_id = us_id;
        this.account_nickname = acc_nickname;
        this.account_balance = acc_bal;

        if(acc_type.equals("checking")){
            this.account_type = accountTypes.checking;
        }
        else if(acc_type.equals("saving")){
            this.account_type = accountTypes.saving;
        }
    }*/

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_nickname() {
        return account_nickname;
    }

    public void setAccount_nickname(String account_nickname) {
        this.account_nickname = account_nickname;
    }

    public float getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(float account_balance) {
        this.account_balance = account_balance;
    }
}
