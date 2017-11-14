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

    static ArrayList<Account> accounts = new ArrayList<>();

    public static void readMergedTSF(String mergedTSF) {

    }

    public static void writeToFile(String newMasterAccountsFilePath, String validAccountsFilePath) {
        try {
            File newMasterAccountsFile = new File(newMasterAccountsFilePath);
            File newValidAccFile = new File(validAccountsFilePath);

            FileWriter masterAccounts = new FileWriter(newMasterAccountsFile);
            BufferedWriter writeMaster = new BufferedWriter(masterAccounts);

            FileWriter validAccounts = new FileWriter(newMasterAccountsFile);
            BufferedWriter writeValid = new BufferedWriter(validAccounts);

            for (Account account : accounts) {
                writeMaster.write(account.toString());
                writeValid.write(account.getAccountNumber());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void run(String oldMasterAccountsFile, String mergedTSF,
            String newMasterAccountsFile, String validAccountsFile) {
        
        try {
            // Reading Account Master File
            FileReader fileReader = new FileReader(oldMasterAccountsFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line, name, balance;

            while ((line = bufferedReader.readLine()) != null) {
                String[] element = line.split(" ");
                balance = element[1];
                name = element[2];
                if (element.length >= 3) {
                    for (int i = 3; i < element.length; i++) {
                        name += element[i];
                    }
                }
                String accNumber = element[0];
                accounts.add(new Account(accNumber, balance, name));

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 4) {
            run(args[0], args[1], args[2], args[3]);
        } else {
            System.out.println("ERROR");
        }

    }

}
