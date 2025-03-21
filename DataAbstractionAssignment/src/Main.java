import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Create a Scanner object so that user input can be read
        Scanner scanner = new Scanner(System.in);
        // Create a Customer object so that the user can access an account
        Customer customer = new Customer();

        // Welcome Text
        System.out.println("Welcome! let's open a bank account!");
        System.out.println("Enter 'deposit', 'withdraw' or 'display' depending on what you want to do.");
        System.out.println("You will also have to enter the amount you want deposited or withdrawn, as");
        System.out.println("well as the type of account which you are accessing.");
        System.out.println("However, if your amount is 0 or has more than two decimal places, nothing will");
        System.out.println("change. Or, if you do not access a valid bank account, nothing will change");
        System.out.println("If you choose 'display' you will be prompted to enter 'deposits' or 'withdraws'");
        System.out.println("to decide which list to display.");
        System.out.println("Or enter 'close' to close the account.");

        // Ask user initial question
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Would you like to deposit, withdraw, display or close:");

        // Scan user input as lowercase letters if they capitalized anything and store it in choice
        String choice = scanner.nextLine().toLowerCase();

        // Let user continually access account
        while(!choice.equals("close")){
            // Depending on what was picked, let the user access things
            switch (choice) {
                // If depositing
                case "deposit":
                    // Ask how much is to be deposited
                    System.out.println("\nHow much would you like to deposit:");
                    // Create variables to store the amount deposited and whether the user input is valid or not
                    double depAmt = 0;
                    boolean depIsValid = false;
                    // Continually check if user entered a double until they do and prompt for another number if they don't
                    while (!depIsValid) {
                        try {
                            depAmt = Double.parseDouble(scanner.nextLine());
                            // When completed, depIsValid will be changed to true and break the loop
                            depIsValid = true;
                        } catch (NumberFormatException e) {
                            // Prompts the user to enter another number
                            System.out.println("\nInvalid number. Please enter another:");
                        }
                    }
                    // Ask which account is to be accessed
                    System.out.println("\nWhich account would you like to deposit into:");
                    // Store user input in a temporary variable
                    String tempDepAccount = scanner.nextLine();
                    // Format the account name, capitalizing only the first letter, and store it in depAccount
                    String depAccount = tempDepAccount.substring(0, 1).toUpperCase() + tempDepAccount.substring(1).toLowerCase();
                    // Determine the current date
                    Date depDate = new Date();
                    // Make deposit in customer object
                    customer.deposit(depAmt, depDate, depAccount);
                    // Display new Balance (just thought I would add this to help make the experience easier)
                    if (depAccount.equals("Checking")) {
                        System.out.println("\nYour new checking balance is $" + customer.getCheckBalance());
                    }
                    else if (depAccount.equals("Saving")) {
                        System.out.println("\nYour new saving balance is $" + customer.getSavingBalance());
                    }
                    break;
                // If withdrawing
                case "withdraw":
                    // Ask how much is to be withdrawn
                    System.out.println("\nHow much would you like to withdraw:");
                    //Create variables to store the amount withdrawn and whether the user input is valid or not
                    double withAmt = 0;
                    boolean withIsValid = false;
                    // Continually check if user entered a double until they do and prompt for another number if they don't
                    while (!withIsValid) {
                        try {
                            withAmt = Double.parseDouble(scanner.nextLine());
                            // When completed, withIsValid will be changed to true and break the loop
                            withIsValid = true;
                        } catch (NumberFormatException e) {
                            // Prompts the user to enter another number
                            System.out.println("\nInvalid number. Please enter another:");
                        }
                    }
                    // Ask which account is to be accessed
                    System.out.println("\nWhich account would you like to withdraw from:");
                    // Store user input in a temporary variable
                    String tempWithAccount = scanner.nextLine();
                    // Format the account name, capitalizing only the first letter, and store it in withAccount
                    String withAccount = tempWithAccount.substring(0, 1).toUpperCase() + tempWithAccount.substring(1).toLowerCase();
                    // Determine current date
                    Date withDate = new Date();
                    // Make withdrawal in customer object
                    customer.withdraw(withAmt, withDate, withAccount);
                    // Display new Balance
                    // If checking was accessed
                    if (withAccount.equals("Checking")) {
                        System.out.println("\nYour new checking balance is $" + customer.getCheckBalance());
                    }
                    // If saving was accessed
                    else if (withAccount.equals("Saving")) {
                        System.out.println("\nYour new saving balance is $" + customer.getSavingBalance());
                    }
                    break;
                // If displaying
                case "display":
                    // Ask if deposits or withdrawals are to be displayed
                    System.out.println("\nWould you like to display your deposits or withdrawals:");
                    // Loop until user enters a valid list, deposits or withdraws
                    boolean listIsValid = false;
                    while (!listIsValid) {
                        // Scan user input as lower case letters
                        String displayChoice = scanner.nextLine().toLowerCase();
                        // Add spacing for readability
                        System.out.println();
                        // Check what is being displayed
                        // If deposits
                        if (displayChoice.equals("deposits")) {
                            // Display deposits
                            customer.displayDeposits();
                            listIsValid = true;
                        }
                        // If withdraws
                        else if (displayChoice.equals("withdraws")) {
                            // Display withdraws
                            customer.displayWithdraws();
                            listIsValid = true;
                        }
                        // Otherwise
                        else {
                            // Prompts the user to enter another account
                            System.out.println("\nInvalid choice. Please enter another");
                        }
                    }
                    break;
                // Otherwise
                default:
                    System.out.println("\nInvalid choice, pick again");
            }
            // Ask user initial question again
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Would you like to deposit, withdraw, display or close:");

            // Scan user input as lowercase letters if they capitalized anything and store it in choice
            choice = scanner.nextLine().toLowerCase();
        }
        // Closing text
        System.out.println("\n\nThank you for banking with us! Come back anytime!");
    }
}
