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
        //convert parameters to correct type
        //this.accountNumber = accountNumber;
        //this.balance = balance;
        this.name = name;
        
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

