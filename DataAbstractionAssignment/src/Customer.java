import java.util.ArrayList;
import java.util.Date;

public class Customer {
    private final int accountNumber;
    private final ArrayList<Deposit> deposits;
    private final ArrayList<Withdraw> withdraws;
    private double checkBalance;
    private double savingBalance;
    private final double savingRate;
    private final String name;
    public static final String CHECKING = "Checking";
    public static final String SAVING = "Saving";
    private final int OVERDRAFT = -100;

    Customer(){
        // Give fields default values
        accountNumber = 123456789;
        deposits = new ArrayList<>();
        withdraws = new ArrayList<>();
        checkBalance = 0;
        savingBalance = 0;
        savingRate = 0.5;
        name = "Name";
    }

    Customer(String name, int accountNumber, double checkDeposit, double savingDeposit){
        // Set input values to respective fields
        this.accountNumber = accountNumber;
        deposits = new ArrayList<>();
        deposit(checkDeposit, new Date(), CHECKING);
        deposit(savingDeposit, new Date(), SAVING);
        withdraws = new ArrayList<>();
        checkBalance = checkDeposit;
        savingBalance = savingDeposit;
        savingRate = 0.5;
        this.name = name;
    }

    // Requires:
    //      - amt must be a positive number with a maximum of 2 decimal places
    //      - date must be the time and date of deposit
    //      - account must be either CHECKING or SAVING
    // Modifies: this, Deposit (creates a new Deposit object and sets its fields)
    // Effects:
    //      - creates a new Deposit object and adds it to deposits
    //      - adds amt to checkBalance or savingBalance (Depends on account)
    public void deposit(double amt, Date date, String account) {
        // Turn the decimal places of the withdrawal into a string
        String decimals = String.valueOf(amt).split("\\.")[1];
        // Check deposit is greater than zero and does not have more than two decimal places
        if (amt > 0 && decimals.length() <= 2) {
            // Determine which account is being accessed
            switch (account) {
                case CHECKING:
                    // Add deposit to checking
                    checkBalance += amt;
                    // Round in case of floating point errors
                    checkBalance = (double) Math.round(checkBalance * 100) / 100;
                    deposits.add(new Deposit(amt, date, account, checkBalance));
                    break;
                case SAVING:
                    // Add deposit to saving
                    savingBalance += amt;
                    // Round in case of floating point errors
                    savingBalance = (double) Math.round(savingBalance * 100) / 100;
                    deposits.add(new Deposit(amt, date, account, savingBalance));
            }
        }
    }

    // Requires:
    //      - amt must be a positive number with a maximum of 2 decimal places
    //      - date must be the time and date of withdrawal
    //      - account must be either CHECKING of SAVING
    // Modifies: this, Withdraw (creates a new Withdraw object anc sets its fields)
    // Effects:
    //      - creates a new Withdraw object and adds it to deposits
    //      - subtracts amt from checkBalance or savingBalance (Depends on account) unless overdrafting
    public void withdraw(double amt, Date date, String account){
        // Turn the decimal places of the withdrawal into a string
        String decimals = String.valueOf(amt).split("\\.")[1];
        // Check withdrawal is greater than zero and does not have more than two decimal places
        if (amt > 0 && decimals.length() <= 2) {
            // Determine which account is being accessed
            switch (account) {
                case CHECKING:
                    // Check if withdrawing leaves balance as less than -$100
                    if (checkBalance - amt < OVERDRAFT) {
                        // Print message stating that amount cannot be withdrawn
                        System.out.println("You cannot withdraw that much");
                    }
                    // Otherwise the balance is greater than -$100
                    else {
                        // Remove withdrawal from checking
                        checkBalance -= amt;
                        // Round in case of floating point errors
                        checkBalance = (double) Math.round(checkBalance * 100) / 100;
                        withdraws.add(new Withdraw(amt, date, account, checkBalance));
                        // Check if overdrafting
                        if (checkOverdraft(amt, account)) {
                            // Print message to user about their overdraft
                            System.out.println("OVERDRAFT! Your balance is now below $0! You can only withdraw up to -$100!");
                        }
                    }
                    break;
                case SAVING:
                    // Check if withdrawing leaves balance as less than -$100
                    if (savingBalance - amt < OVERDRAFT) {
                        // Print message stating that amount cannot be withdrawn
                        System.out.println("You cannot withdraw that much");
                    }
                    // Otherwise the balance is greater than -$100
                    else {
                        // Remove withdrawal from saving
                        savingBalance -= amt;
                        // Round in case of floating point errors
                        savingBalance = (double) Math.round(savingBalance * 100) / 100;
                        withdraws.add(new Withdraw(amt, date, account, savingBalance));
                        // Check if overdrafting
                        if (checkOverdraft(amt, account)) {
                            // Print message to user about their overdraft
                            System.out.println("OVERDRAFT! Your balance is now below $0! You can only withdraw up to -$100!");
                        }
                    }
            }
        }
    }

    private boolean checkOverdraft(double amt, String account){
        boolean isOverdraft = false;
        // Determine which account is being accessed
        switch (account) {
            case CHECKING:
                // Check if overdrafting from checking
                if (checkBalance - amt < 0) {
                    isOverdraft = true;
                }
                break;
            case SAVING:
                // Check if overdrafting from saving
                if (savingBalance - amt < 0) {
                    isOverdraft = true;
                }
        }
        return isOverdraft;
    }

    //do not modify
    public void displayDeposits(){
        for(Deposit d : deposits){
            System.out.println(d);
        }
    }

    //do not modify
    public void displayWithdraws(){
        for(Withdraw w : withdraws){
            System.out.println(w);
        }
    }

    // Getters
    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public ArrayList<Withdraw> getWithdraws() {
        return withdraws;
    }

    public double getCheckBalance() {
        return checkBalance;
    }

    public double getSavingBalance() {
        return savingBalance;
    }
}
