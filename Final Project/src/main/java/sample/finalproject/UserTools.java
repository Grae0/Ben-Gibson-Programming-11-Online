package sample.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.*;

public class UserTools {

    // Requires: none
    // Modifies: none
    // Effects: returns false if "users.txt" is empty, otherwise returns true
    public static boolean usersExist() throws IOException {
        // FileReader and BufferedReader to read from "users.txt"
        FileReader fr = new FileReader("users.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read the first line in "users.txt" and assign it to line. If the line is empty it will be read as null
        String line = br.readLine();

        // Close the BufferedReader
        br.close();

        // If line is not null, return true, otherwise return false
        return line != null;
    }

    // Requires: none
    // Modifies: none
    // Effects: returns an ArrayList of the usernames in "users.txt"
    public static ArrayList<String> getUsernames() throws IOException {
        // ArrayList of usernames that already exist
        ArrayList<String> usernames = new ArrayList<>();

        // integer to help determine what the current line being read is
        int currentLine = 0;

        // FileReader and BufferedReader to read from "users.txt"
        FileReader fr = new FileReader("users.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read through "users.txt"
        String line;
        while ((line = br.readLine()) != null) {
            // If currentLine is a multiple of 6 (meaning the current line is a username)
            if (currentLine % 6 == 0) {
                // Add the current line to usernames
                usernames.add(line);
            }

            // Add 1 to the currentLine
            currentLine++;
        }

        // Close the BufferedReader
        br.close();

        // Return usernames
        return usernames;
    }

    // Requires: username of file being searched, cannot be null
    // Modifies: none
    // Effects: returns an ArrayList of all the Entries in "(username) entries.txt"
    public static ArrayList<Entry> getEntries(String username) throws IOException {
        // String for the title and StringBuilder for the contents
        String title = "";
        StringBuilder contents = new StringBuilder();

        // integers to determine what the current line being read is
        int currentLine = 0;
        int currentLength = 2;

        // Also create a list of Entries
        ArrayList<Entry> entries = new ArrayList<>();

        // Create a FileReader and BufferedReader to read from "(username) entries.txt"
        FileReader fr = new FileReader(username + " entries.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read "(username) entries.txt"
        String line;
        while ((line = br.readLine()) != null) {
            // If the currentLine is 0 (meaning the current line is a title)
            if (currentLine == 0) {
                // Set the title to this line
                title = line;
            }
            // If the currentLine is 1 (meaning the current line is a length)
            else if (currentLine == 1) {
                // Set the currentLength to this line as an integer
                currentLength = Integer.parseInt(line) - 1;
            }
            // Otherwise the line is part of contents
            else {
                contents.append(line).append("\n");
            }

            // If the currentLine is the currentLength (meaning the current line is the last line in the current Entry)
            if (currentLine == currentLength) {
                // Reset the currentLine to 0
                currentLine = 0;
                // Add a new Entry to entries
                entries.add(new Entry(username, title, contents.substring(0, contents.length() - 1)));
                // Clear contents
                contents = new StringBuilder();

            }
            // Otherwise the currentLine is not the last line of the Entry
            else {
                // Add 1 to currentLine
                currentLine++;
            }
        }

        return entries;
    }

    // Requires: instructions to display corrections to user
    //           username, password and confirmed password of user
    // Modifies: instructions (sets text)
    // Effects: returns true if the username, password and confirmed password are valid, otherwise returns false and updates instructions
    public static boolean signupIsValid(Label instructions, String username, String password, String confirmed) throws IOException{
        // ArrayList of existing usernames that uses getUsernames
        ArrayList<String> usernames = getUsernames();

        // If username is empty
        if (username.isEmpty()) {
            instructions.setText("Username cannot be empty");
        }
        // If username is contained in usernames (already in use by another user)
        else if (usernames.contains(username)) {
            instructions.setText("Username taken. Please choose another");
        }
        // If password is less than 4 characters
        else if (password.length() < 4) {
            instructions.setText("Password must be at least 4 characters long");
        }
        // If confirm password is empty
        else if (confirmed.isEmpty()) {
            instructions.setText("Confirm your password");
        }
        // If password and confirm password do not match
        else if (!password.equals(confirmed)) {
            instructions.setText("Passwords do not match");
        }
        // Otherwise the information entered is valid
        else {
            return true;
        }

        // If true was not returned, return false
        return false;
    }

    // Requires: instructions to display corrections to user
    //           password of user
    // Modifies: instructions (sets text)
    // Effects: return true if edited information is valid, otherwise return false and change instructions
    public static boolean editIsValid(Label instructions, String password) {
        // If password is less than 4 characters
        if (password.length() < 4) {
            instructions.setText("Password must be at least 4 characters long");
        }
        // If password is greater than 15 characters
        else if (password.length() > 15) {
            instructions.setText("Password must be less than 16 characters long");
        }
        // Otherwise the information entered is valid
        else {
            // Return true
            return true;
        }

        // If true was not returned, return false
        return false;
    }

    // Requires: username of user
    // Modifies: none
    // Effects: returns ArrayList of password, name, email, phone and address of user
    public static ArrayList<String> getFields(String username) throws IOException {
        // integer to help determine what the current line being read is
        int currentLine = 0;
        // boolean that determines if the user has been found
        boolean isFound = false;

        // ArrayList of fields in user
        ArrayList<String> fields = new ArrayList<>();

        // FileReader and BufferedReader to read from "users.txt"
        FileReader fr = new FileReader("users.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read through users.txt
        String line;
        while ((line = br.readLine()) != null) {

            // If currentLine is 0 (meaning the current line is a username)
            if (currentLine == 0) {
                // If the current line is equal to the username entered
                if (line.equals(username)) {
                    // Set isFound to true
                    isFound = true;
                }
            }
            // If the user has been found, meaning the current line is part of the user
            else if (isFound) {
                // Check different cases of currentLine
                switch (currentLine) {
                    // If currentLine is 1 (meaning the current line is the password)
                    case (1):
                        // Add the password to fields
                        fields.add(line);
                        break;
                    // If currentLine is 2 (meaning the current line is the name)
                    case (2):
                        // Add the name to fields
                        fields.add(line);
                        break;
                    // If currentLine is 3 (meaning the current line is the email)
                    case (3):
                        // Add the email to fields
                        fields.add(line);
                        break;
                    // If currentLine is 4 (meaning the current line is the phone)
                    case (4):
                        // Add the phone to fields
                        fields.add(line);
                        break;
                    // If currentLine is 5 (meaning the current line is the address)
                    case (5):
                        // Add the address to fields
                        fields.add(line);
                }
            }

            // If currentLine is 5 (meaning the current line is an address, the last line before the next username)
            if (currentLine == 5) {
                // Reset currentLine to 0
                currentLine = 0;
            }
            // Otherwise the currentLine is not 6 (meaning the current line is not the address, the last line)
            else {
                // Add 1 to the currentLine
                currentLine++;
            }
        }

        // Close the BufferedReader
        br.close();

        // Return fields
        return fields;
    }
}
