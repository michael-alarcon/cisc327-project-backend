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
                Account accountToWrite = accountsMap.get(account);
                writeMaster.write(account.toString());
                writeValid.write(account.getAccountNumber());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void run(String oldMasterAccountsFile, String mergedTSF,
            String newMasterAccountsFile, String validAccountsFile) throws FileNotFoundException, IOException {

        // Reading Account Master File
        FileReader fileReader = new FileReader(oldMasterAccountsFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        String name = "";

        while ((line = bufferedReader.readLine()) != null) {
            String[] element = line.split(" ");
            for (int i = 2; i < element.length; i++) {
                name.append(element[i]);
            }
            String accNumber = element[0];
            accountsMap.put(accNumber, new Account(element[1], name));
            accounts.add(accountsMap.get(accNumber));

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
