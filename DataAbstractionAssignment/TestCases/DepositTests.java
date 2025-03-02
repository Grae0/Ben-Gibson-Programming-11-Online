import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DepositTests {

    @Test
    public void testToStringChecking() {
        // Initialize arguments
        double amount = 63;
        Date date = new Date(0);
        double balance = 59.26;

        // Make deposit
        Deposit deposit = new Deposit(amount, date, Customer.CHECKING, balance);

        // Check toString returns the correct format
        String expected = "Deposit of: $63.0 Date: Wed Dec 31 16:00:00 PST 1969 into account: Checking " +
                "Current Balance in Checking is: $59.26";
        String actual = deposit.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringSaving() {
        // Initialize arguments
        double amount = 72.39;
        Date date = new Date(1735718400000L);
        double balance = -15;

        // Make deposit
        Deposit deposit = new Deposit(amount, date, Customer.SAVING, balance);

        // Check toString returns the correct format
        String expected = "Deposit of: $72.39 Date: Wed Jan 01 00:00:00 PST 2025 into account: Saving " +
                "Current Balance in Saving is: -$15.00";
        String actual = deposit.toString();
        assertEquals(expected, actual);
    }
}
