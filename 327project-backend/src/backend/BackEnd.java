package backend;

import java.io.*;
import java.util.*;

/**
 * This code is responsible for handling the backend of the QBasic project. It
 * takes in 4 inputs: the old master accounts file, the merged transaction
 * summary file, the new master accounts file, and the new valid accounts file.
 * Reads through the old master accounts fil eand the merged transaction
 * summary file and creates two new files.
 * 
 * @author Rory Hilson 10203174
 * @author Neil Huan 10189880
 * @author Michael Alarcon 10172841
 */
public class BackEnd {

    static ArrayList<Account> accountsList = new ArrayList<>();
    static HashMap<Integer, Account> accountsMap = new HashMap<>();

    /**
     * Reads the merged transaction summary file. Depending on the commands
     * found in the file accounts will either be updated, created or deleted.
     * 
     * @param mergedTSF File name of the merged transaction summary files.
     */
    public static void readMergedTSF(String mergedTSF) {
        try {
            // Reading Account Master File
            FileReader fileReader = new FileReader(mergedTSF);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line, command = "", name = "";
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
                accountsMap.put(accountNumber, new Account(accountNumber, balance, name));
            }
            if (accountsMap.containsKey(accountNumber) || accountsMap.containsKey(accountNumber2)) {
                Account toAcct = accountsMap.get(accountNumber);
                Account toAcct2 = accountsMap.get(accountNumber2);
                if (command.equals("DEP")) {
                    toAcct.deposit(balance);
                }
                if (command.equals("WDR")) {
                    toAcct2.withdraw(balance);
                }
                if (command.equals("DEL")) {
                    accountsMap.remove(accountNumber);
                }
                if (command.equals("XFR")) {
                    toAcct.deposit(balance);
                    toAcct2.withdraw(balance);
                }
            } else if (command.equals("NEW")) {
                if (accountsMap.containsKey(accountNumber)) {
                    System.out.println("Account number " + accountNumber + " already exists");
                } else {
                    accountsMap.put(accountNumber, new Account(accountNumber, balance, name));
                }
            }
//            if (command.equals("DEP")) {
//                for (Account toAcct : accountsList) {
//                    if (accountNumber == toAcct.getAccountNumber()) {
//                        toAcct.deposit(balance);
//                    }
//                }
//            } else if (command.equals("WDR")) {
//                for (Account toAcct : accountsList) {
//                    if (accountNumber == toAcct.getAccountNumber()) {
//                        toAcct.withdraw(balance);
//                    }
//                }
//            } else if (command.equals("NEW")) {
//                Account NAcct = new Account(accountNumber, 0, name);
//                //add to list
//            } else if (command.equals("DEL")) {
//                for (Account toAcct : accountsList) {
//                    if (accountNumber == toAcct.getAccountNumber()) {
//                        toAcct.delete();
//                    }
//                }
//                //toAcct.remove?
//            } else if (command.equals("XFR")) {
//                for (Account toAcct : accountsList) {
//                    if (accountNumber == toAcct.getAccountNumber()) {
//                        toAcct.deposit(balance);
//                    }
//                }
//                for (Account toAcct : accountsList) {
//                    if (accountNumber2 == toAcct.getAccountNumber()) {
//                        toAcct.withdraw(balance);
//                    }
//                }
//            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Writes the new master accounts file and new valid accounts file. Loops
     * through the accounts in the map and writes the appropriate information to
     * their respective files.
     *
     * @param newMasterAccountsFileName File name of the new master accounts
     * file
     * @param newValidAccountsFileName File name of the new valid accoutns file
     */
    public static void writeValidFiles(String newMasterAccountsFileName, String newValidAccountsFileName) {
        try {
            String currentFolder = System.getProperty("user.dir") + "\\";
            
            File newMasterAccountsFile = new File(currentFolder + newMasterAccountsFileName);
            File newValidAccountsFile = new File(newValidAccountsFileName);

            FileWriter fwMasterAccounts = new FileWriter(currentFolder + newMasterAccountsFile);
            BufferedWriter writeToMasterAccounts = new BufferedWriter(fwMasterAccounts);

            FileWriter validAccounts = new FileWriter(newMasterAccountsFile);
            BufferedWriter writeToValidAccounts = new BufferedWriter(validAccounts);

            Account account;
            for (int accountNumberKey : accountsMap.keySet()) {
                account = accountsMap.get(accountNumberKey);
                writeToMasterAccounts.write(account.toString());
                writeToValidAccounts.write(account.getAccountNumber());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads the old master accounts file and stores accounts in a map.
     *
     * @param oldMasterAccountsFileName File that contains the old accounts
     */
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
                accountsMap.put(accountNumber, new Account(accountNumber, balance, accountName));

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Main function.
     * 
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
