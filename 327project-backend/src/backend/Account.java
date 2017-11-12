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
    
    private int number;
    private long balance;
    private String name;

    public Account(int number, long money, String name) {
        this.number = number;
        this.balance = money;
    }
    
    protected int getAccountNumber() {
        return number;
    }
}

