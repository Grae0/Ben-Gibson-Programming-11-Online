import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Create customer and deposit into accounts (deposit in different ways)
        Customer Ben = new Customer("Ben", 987654321, 1200, 0);
        Ben.deposit(95.36, new Date(), Customer.SAVING);

        // Withdraw from accounts
        Ben.withdraw(481.24, new Date(), Customer.CHECKING);
        Ben.withdraw(100, new Date(), Customer.SAVING);

        // Display deposits and withdrawals
        Ben.displayDeposits();
        Ben.displayWithdraws();
    }
}
