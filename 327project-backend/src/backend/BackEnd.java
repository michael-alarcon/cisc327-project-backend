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

    public static void writeToFile(String newMasterAccountsFilePath, String validAccountsFilePath) throws IOException {
        File newMasterAccountsFile = new File(newMasterAccountsFilePath);
        File newValidAccFile = new File(validAccountsFilePath);
        
        FileWriter masterAccounts = new FileWriter(newMasterAccountsFile);
        BufferedWriter writeMaster = new BufferedWriter(masterAccounts);
        
        FileWriter validAccounts = new FileWriter(newMasterAccountsFile);
        BufferedWriter writeValid = new BufferedWriter(validAccounts);
        
        for (Account account : accounts) {
            
            writeMaster.write(account.toString());
            writeMaster.newLine();
            
            writeValid.write(account.getAccountNumber());
            writeValid.newLine();
        }
    }

    public static void run(String oldMasterAccountsFilePath, String mergedTSF, String newMasterAccountsFile, String validAccountsFile) {

        try {
            writeToFile(newMasterAccountsFile, validAccountsFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        readOldMasterAccounts.readFile();
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

        // READING FILES
    }

}
