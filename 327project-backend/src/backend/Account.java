package backend;

/**
 * Account object that keeps the information of the account: account number,
 * balance, and account name (owner of the account).
 * 
 * @author Rory Hilson 10203174
 * @author Neil Huan 10189880
 * @author Michael Alarcon 10172841
 */
public class Account {
    
    private final int accountNumber;
    private long balance;
    private final String name;

    /**
     * Constructor for the object
     * 
     * @param accountNumber An integer of length 7
     * @param balance Balance of account in cents
     * @param name Name of account owner
     */
    public Account(int accountNumber, long balance, String name) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.name = name;
    }
    
    /**
     * Deposits money into the account, adds to balance.
     * 
     * @param amount Value to be added to balance
     */
    public void deposit(long amount){
        this.balance += amount;
    }
    
    /**
     * Withdraws money from the account, subtracts from balance.
     * 
     * @param amount Value to be subtracted.
     */
    public void withdraw(long amount){
        this.balance -= amount;
    }
    
    /**
     * Retrieves the account number.
     * 
     * @return The account number
     */
    protected int getAccountNumber() {
        return this.accountNumber;
    }
    
    /**
     * Retrieves the account name.
     * 
     * @return The account name
     */
    protected String getName() {
        return this.name;
    }
    
    /**
     * Retrieves the account balance.
     * 
     * @return The account balance
     */
    protected long getBalance() {
        return this.balance;
    }
    
    @Override
    /**
     * Returns a string containing all information about the account
     */
    public String toString() {
        return this.accountNumber + " " + this.balance + " " + this.name;
    }
}

