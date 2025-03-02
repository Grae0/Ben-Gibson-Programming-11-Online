import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTests {
    Customer customer;

    @Before
    public void setup() {
        customer = new Customer();
    }

    @Test
    public void testDepositCheckingValid() {
        // Initialize arguments
        double amount1 = 25.68;
        double amount2 = 124.2;
        Date date1 = new Date(0);
        Date date2 = new Date(86400000);

        // Initial balance should be zero
        assertEquals(0, customer.getDeposits().size());
        assertEquals(0, customer.getCheckBalance(), 0);

        // Make first deposit
        customer.deposit(amount1, date1, Customer.CHECKING);

        // Check correct amount has been deposited
        assertEquals(1, customer.getDeposits().size());
        assertEquals(25.68, customer.getDeposits().getFirst().getAmount(), 0);
        assertEquals(25.68, customer.getDeposits().getFirst().getBalance(), 0);
        assertEquals(25.68, customer.getCheckBalance(), 0);

        // Make second deposit
        customer.deposit(amount2, date2, Customer.CHECKING);

        // Check correct amount has been deposited
        assertEquals(2, customer.getDeposits().size());
        assertEquals(124.2, customer.getDeposits().get(1).getAmount(), 0);
        assertEquals(149.88, customer.getDeposits().get(1).getBalance(), 0);
        assertEquals(149.88, customer.getCheckBalance(), 0);
    }

    @Test
    public void testDepositSavingValid() {
        // Initialize arguments
        double amount1 = 25.68;
        double amount2 = 124.2;
        Date date1 = new Date(0);
        Date date2 = new Date(86400000);

        // Initial balance should be zero
        assertEquals(0, customer.getDeposits().size());
        assertEquals(0, customer.getSavingBalance(), 0);

        // Make first deposit
        customer.deposit(amount1, date1, Customer.SAVING);

        // Check correct amount has been deposited
        assertEquals(1, customer.getDeposits().size());
        assertEquals(25.68, customer.getDeposits().getFirst().getAmount(), 0);
        assertEquals(25.68, customer.getDeposits().getFirst().getBalance(), 0);
        assertEquals(25.68, customer.getSavingBalance(), 0);

        // Make second deposit
        customer.deposit(amount2, date2, Customer.SAVING);

        // Check correct amount has been deposited
        assertEquals(2, customer.getDeposits().size());
        assertEquals(124.2, customer.getDeposits().get(1).getAmount(), 0);
        assertEquals(149.88, customer.getDeposits().get(1).getBalance(), 0);
        assertEquals(149.88, customer.getSavingBalance(), 0);
    }

    @Test
    public void testDepositInvalid() {
        // Initialize valid parameters
        double amount = 100;
        Date date = new Date(0);
        String account = Customer.CHECKING;

        // Initial balance should be zero
        assertEquals(0, customer.getDeposits().size());
        assertEquals(0, customer.getCheckBalance(), 0);

        // Make multiple invalid deposits
        customer.deposit(14.228, date, account); // More than 2 decimal places
        customer.deposit(0, date, account); // No deposit
        customer.deposit(-100, date, account); // Negative deposit
        customer.deposit(amount, date, "checking"); // Invalid account name
        customer.deposit(amount, date, "Hello"); // Invalid account name

        // Check nothing was deposited
        assertEquals(0, customer.getDeposits().size());
        assertEquals(0, customer.getCheckBalance(), 0);
    }

    @Test
    public void testWithdrawCheckingValid() {
        // Initialize arguments
        double amount1 = 25.68;
        double amount2 = 74.32;
        Date date1 = new Date(0);
        Date date2 = new Date(86400000);

        // Initial balance should be zero
        assertEquals(0, customer.getWithdraws().size());
        assertEquals(0, customer.getCheckBalance(), 0);

        // Make first withdrawal
        customer.withdraw(amount1, date1, Customer.CHECKING);

        // Check correct amount has been withdrawn
        assertEquals(1, customer.getWithdraws().size());
        assertEquals(25.68, customer.getWithdraws().getFirst().getAmount(), 0);
        assertEquals(-25.68, customer.getWithdraws().getFirst().getBalance(), 0);
        assertEquals(-25.68, customer.getCheckBalance(), 0);

        // Make second withdrawal
        customer.withdraw(amount2, date2, Customer.CHECKING);

        // Check correct amount has been withdrawn (Overdraft so not actual amount is being withdrawn)
        assertEquals(2, customer.getWithdraws().size());
        assertEquals(74.32, customer.getWithdraws().get(1).getAmount(), 0);
        assertEquals(-100, customer.getWithdraws().get(1).getBalance(), 0);
        assertEquals(-100, customer.getCheckBalance(), 0);
    }

    @Test
    public void testWithdrawSavingValid() {
        // Initialize arguments
        double amount1 = 25.68;
        double amount2 = 74.32;
        Date date1 = new Date(0);
        Date date2 = new Date(86400000);

        // Initial balance should be zero
        assertEquals(0, customer.getWithdraws().size());
        assertEquals(0, customer.getSavingBalance(), 0);

        // Make first withdrawal
        customer.withdraw(amount1, date1, Customer.SAVING);

        // Check correct amount has been withdrawn
        assertEquals(1, customer.getWithdraws().size());
        assertEquals(25.68, customer.getWithdraws().getFirst().getAmount(), 0);
        assertEquals(-25.68, customer.getWithdraws().getFirst().getBalance(), 0);
        assertEquals(-25.68, customer.getSavingBalance(), 0);

        // Make second withdrawal
        customer.withdraw(amount2, date2, Customer.SAVING);

        // Check correct amount has been withdrawn (Overdraft so not actual amount is being withdrawn)
        assertEquals(2, customer.getWithdraws().size());
        assertEquals(74.32, customer.getWithdraws().get(1).getAmount(), 0);
        assertEquals(-100, customer.getWithdraws().get(1).getBalance(), 0);
        assertEquals(-100, customer.getSavingBalance(), 0);
    }

    @Test
    public void testWithdrawInvalid() {
        // Initialize valid amounts
        double amount = 100;
        Date date = new Date(0);
        String account = Customer.CHECKING;

        // Initial balance should be zero
        assertEquals(0, customer.getWithdraws().size());
        assertEquals(0, customer.getCheckBalance(), 0);

        // Make multiple invalid withdrawals
        customer.withdraw(1000, date, account); // Overdraft, no money should be withdrawn
        customer.withdraw(14.228, date, account); // More than 2 decimal places
        customer.withdraw(0, date, account); // No withdrawal
        customer.withdraw(-100, date, account); // Negative withdrawal
        customer.withdraw(amount, date, "checking"); // Invalid account name
        customer.withdraw(amount, date, "Hello"); // Invalid account name

        // Check nothing was withdrawn
        assertEquals(0, customer.getDeposits().size());
        assertEquals(0, customer.getCheckBalance(), 0);
    }
}
