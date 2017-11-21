package backend;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This code is responsible for handling the backend of the QBasic project. It
 * takes in 4 inputs: the old master accounts file, the merged transaction
 * summary file, the new master accounts file, and the new valid accounts file.
 * Reads through the old master accounts file and the merged transaction summary
 * file and creates two new files. Assumes input files are well formed.
 *
 * @author Rory Hilson 10203174
 * @author Neil Huan 10189880
 * @author Michael Alarcon 10172841
 */
public class BackEnd {

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

            // Create logger
            Logger errorLog = Logger.getLogger("Error");
            FileHandler errorFile;
            errorFile = new FileHandler(System.getProperty("user.dir") + "\\errorLogFile.log");
            errorLog.addHandler(errorFile);
            SimpleFormatter formatter = new SimpleFormatter();
            errorFile.setFormatter(formatter);

            String line, command, name;
            long money;
            int accountNumber, accountNumber2;

            // reads merged transaction summary file line by line
            while ((line = bufferedReader.readLine()) != null) {
                
                if (line.equals("EOS")) {
                    break;
                }
                
                String[] element = line.split(" ");
                command = element[0];
                accountNumber = Integer.parseInt(element[1]);
                money = Long.parseLong(element[2]);
                accountNumber2 = Integer.parseInt(element[3]);
                name = element[4];

                // getting the full name
                if (element.length >= 3) {
                    for (int i = 3; i < element.length; i++) {
                        name += element[i];
                    }
                }

                if (command.equals("NEW")) {
                    if (accountsMap.containsKey(accountNumber)) {
                        errorLog.log(Level.WARNING, "Account {0} already exists", accountNumber);
                    } else {
                        accountsMap.put(accountNumber, new Account(accountNumber, money, name));
                    }
                } else if (accountsMap.containsKey(accountNumber) || accountsMap.containsKey(accountNumber2)) {
                    
                    Account toAcct = accountsMap.get(accountNumber);
                    Account toAcct2 = accountsMap.get(accountNumber2);
                    
                    if (command.equals("DEP")) {
                        toAcct.deposit(money);
                    }
                    if (command.equals("WDR")) {
                        if ((toAcct2.getBalance() - money) < 0) {
                            errorLog.warning("Insufficient Funds");
                        } else {
                            toAcct2.withdraw(money);
                        }
                    }
                    if (command.equals("DEL")) {
                        if (!accountsMap.containsKey(accountNumber)) {
                            errorLog.log(Level.WARNING, "Account {0} does not exist", accountNumber);
                        } else if (toAcct.getBalance() != 0) {
                            errorLog.log(Level.WARNING, "Account {0} does not have $0 balance", accountNumber);
                        } else {
                            accountsMap.remove(accountNumber);
                        }
                    }
                    if (command.equals("XFR")) {
                        if ((toAcct2.getBalance() - money) < 0) {
                            errorLog.warning("Insufficient Funds");
                        }
                        toAcct.deposit(money);
                        toAcct2.withdraw(money);
                    }
                }
            }
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
     * @param newValidAccountsFileName File name of the new valid accounts file
     */
    public static void writeValidFiles(String newMasterAccountsFileName, String newValidAccountsFileName) {
        try {
            String currentFolder = System.getProperty("user.dir") + "\\";   //current folder

            //create file for new master accounts file
            File newMasterAccountsFile = new File(currentFolder + newMasterAccountsFileName);
            FileWriter fwMasterAccounts = new FileWriter(newMasterAccountsFile);
            BufferedWriter writeToMasterAccounts = new BufferedWriter(fwMasterAccounts);

            //create file for new valid accounts file
            File newValidAccountsFile = new File(currentFolder + newValidAccountsFileName);
            FileWriter validAccounts = new FileWriter(newValidAccountsFile);
            BufferedWriter writeToValidAccounts = new BufferedWriter(validAccounts);
            
            if (newValidAccountsFile.exists()) {
                newValidAccountsFile.delete();
            }
            if (newMasterAccountsFile.exists()) {
                newMasterAccountsFile.delete();
            }

            // sorts accounts by number and writes to files
            ArrayList<Integer> sortedAccountsList = new ArrayList<>(accountsMap.size());
            sortedAccountsList.addAll(accountsMap.keySet());
            Account anAccount;

            for (int accountNumberKey : sortedAccountsList) {
                anAccount = accountsMap.get(accountNumberKey);
                writeToMasterAccounts.write(anAccount.toString());
                writeToMasterAccounts.newLine();
                writeToValidAccounts.write(anAccount.getAccountNumber() + "");
                writeToValidAccounts.newLine();
            }
            
            writeToMasterAccounts.flush();
            writeToMasterAccounts.close();
            writeToValidAccounts.flush();
            writeToValidAccounts.close(); 
            
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

            //reads line by line
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
