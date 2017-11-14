/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.io.File;

/**
 *
 * @author Neil
 */
public class Account {
    
    private int accountNumber;
    private long balance;
    private String name;

    public Account(String accountNumber, String balance, String name) {
        this.accountNumber = Integer.parseInt(accountNumber);
        this.balance = Long.parseLong(balance);
        this.name = name;
    }
    
    public void deposit(String amount){
        this.balance += Long.parseLong(amount);
    }
    
    public void withdraw(String amount){
        this.balance -= Long.parseLong(amount);
    }
    
    protected int getAccountNumber() {
        return this.accountNumber;
    }
    
    protected String getName() {
        return this.name;
    }
    
    protected long getBalance() {
        return this.balance;
    }
    
    @Override
    public String toString() {
        return this.accountNumber + " " + this.balance + " " + this.name;
    }
}

