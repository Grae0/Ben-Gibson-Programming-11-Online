import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class WithdrawTests {

    @Test
    public void testToStringChecking() {
        // Initialize arguments
        double amount = 215;
        Date date = new Date(0);
        double balance = 372.19;

        // Make withdrawal
        Withdraw withdraw = new Withdraw(amount, date, Customer.CHECKING, balance);

        // Check toString returns the correct format
        String expected = "Withdrawal of: $215.0 Date: Wed Dec 31 16:00:00 PST 1969 from account: Checking " +
                "Current Balance in Checking is: $372.19";
        String actual = withdraw.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringSaving() {
        // Initialize arguments
        double amount = 1235;
        Date date = new Date(1735718400000L);
        double balance = -50;

        // Make withdrawal
        Withdraw withdraw = new Withdraw(amount, date, Customer.SAVING, balance);

        // Check toString returns the correct format
        String expected = "Withdrawal of: $1235.0 Date: Wed Jan 01 00:00:00 PST 2025 from account: Saving " +
                "Current Balance in Saving is: -$50.00";
        String actual = withdraw.toString();
        assertEquals(expected, actual);
    }
}
