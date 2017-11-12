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
    
    private String balance;
    private String name;

    public Account(String balance, String name) {
        this.balance = balance;
    }
    
    protected int getAccountNumber() {
        return number;
    }
}

