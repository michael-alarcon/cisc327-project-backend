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

            String line, accountName, balance, accountNumber;

            while ((line = bufferedReader.readLine()) != null) {
                String[] element = line.split(" ");
                balance = element[1];
                accountName = element[2];
                if (element.length >= 3) {
                    for (int i = 3; i < element.length; i++) {
                        accountName += element[i];
                    }
                }
                accountNumber = element[0];
                accountsList.add(new Account(accountNumber, balance, accountName));

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
