import java.util.Date;

public class Deposit {
    private final double amount;
    private final Date date;
    private final String account;
    private final double balance;

    Deposit(double amount, Date date, String account, double balance){
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.balance = balance;
    }

    // Requires: None
    // Modifies: None
    // Effects: Returns a String representation of the Deposit in the proper format
    public String toString() {
        // If balance has no decimal places add two zeros for proper format
        // Use absolute value because negative sign will be added before dollar sign later
        String stringBalance = Double.toString(Math.abs(balance));
        if (balance % 1 == 0) {
            stringBalance += "0";
        }
        // If balance is negative place negative sign before dollar sign
        if (balance < 0) {
            stringBalance = "-$" + stringBalance;
        }
        else {
            stringBalance = "$" + stringBalance;
        }
        // Return string in proper format
        return "Deposit of: $" + amount + " Date: " + date + " into account: " + account +
                " Current Balance in " + account + " is: " + stringBalance;
    }

    // Getters
    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }
}
