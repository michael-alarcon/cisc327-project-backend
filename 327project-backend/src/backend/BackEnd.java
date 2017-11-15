/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.io.*;
import java.util.*;

/**
 *
 * @author Michael Alarcon
 */
public class BackEnd {

    static ArrayList<Account> accountsList = new ArrayList<>();

    public static void readMergedTSF(String mergedTSF) {
        try {
            // Reading Account Master File
            FileReader fileReader = new FileReader(mergedTSF);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line, command = "", name; 
            long balance = 0;  
            int accountNumber = 0, accountNumber2 = 0;
            

            while ((line = bufferedReader.readLine()) != null) {
                String[] element = line.split(" ");
                command = element[0];
                accountNumber = Integer.parseInt(element[1]);
                balance = Long.parseLong(element[2]);
                accountNumber2 = Integer.parseInt(element[3]);
                name = element[4];
                if (element.length >= 3) {
                    for (int i = 3; i < element.length; i++) {
                        name += element[i];
                    }
                }
                accountsList.add(new Account(accountNumber, balance, name));
            }
            
            if (command.equals("DEP")){
            for(Account toAcct:accountsList)
                if (accountNumber == toAcct.getAccountNumber())
                    toAcct.deposit(balance);
        } else if (command.equals("WDR")){
            for(Account toAcct:accountsList)
                if (accountNumber == toAcct.getAccountNumber())
                    toAcct.withdraw(balance);        
        } else if (command.equals("NEW")){
            Account NAcct = new Account(accountNumber,0,name);
            //add to list
        } else if (command.equals("DEL")){
            for(Account toAcct:accountsList)
                if (accountNumber == toAcct.getAccountNumber())
                    toAcct.delete();
                    //toAcct.remove?
        } else if (command.equals("XFR")){
            for(Account toAcct:accountsList)
                if (accountNumber == toAcct.getAccountNumber())
                    toAcct.deposit(balance);
            for(Account toAcct:accountsList)
                if (accountNumber2 == toAcct.getAccountNumber())
                    toAcct.withdraw(balance);
        }
        
            
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void writeValidFiles(String newMasterAccountsFileName, String validAccountsFileName) {
        try {
            File newMasterAccountsFile = new File(newMasterAccountsFileName);
            File newValidAccountsFile = new File(validAccountsFileName);

            FileWriter fwMasterAccounts = new FileWriter(newMasterAccountsFile);
            BufferedWriter writeToMasterAccounts = new BufferedWriter(fwMasterAccounts);

            FileWriter validAccounts = new FileWriter(newMasterAccountsFile);
            BufferedWriter writeToValidAccounts = new BufferedWriter(validAccounts);

            for (Account account : accountsList) {
                writeToMasterAccounts.write(account.toString());
                writeToValidAccounts.write(account.getAccountNumber());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readOldMasterAccountsFile(String oldMasterAccountsFileName) {

        try {
            // Reading Account Master File
            FileReader fileReader = new FileReader(oldMasterAccountsFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] element;
            String line, accountName; 
            int accountNumber; 
            long balance;

            while ((line = bufferedReader.readLine()) != null) {
                element = line.split(" ");
                accountNumber = Integer.parseInt(element[0]);
                balance = Long.parseLong(element[1]);
                accountName = element[2];
                if (element.length >= 3) {
                    for (int i = 3; i < element.length; i++) {
                        accountName += element[i];
                    }
                }
                accountsList.add(new Account(accountNumber, balance, accountName));

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int toInt(String input) {
        return Integer.parseInt(input);
    }

    public static long toLong(String input) {
        return Long.parseLong(input);
    }

    /**
     * @param args 0 - old master accountsList file 1 - merged transaction
     * summary file 2 - new master accountsList file 3 - valid accountsList file
     */
    public static void main(String[] args) {
        if (args.length == 4) {
            readOldMasterAccountsFile(args[0]);
            readMergedTSF(args[1]);
            writeValidFiles(args[2], args[3]);
        } else {
            System.out.println("ERROR");
        }

    }

}
