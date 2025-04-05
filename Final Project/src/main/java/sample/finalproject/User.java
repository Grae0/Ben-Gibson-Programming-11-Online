package sample.finalproject;

import java.io.*;
import java.util.ArrayList;

public class User {
    // Fields for user
    private final String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;

    // Requires: username and password of user
    // Modifies: this
    // Effects: a constructor, sets the user's username and password to the arguments given
    //          this constructor is used when signing up
    public User(String username, String password) {
        // Set username and password to arguments given
        this.username = username;
        this.password = password;
        // Set other fields to empty strings
        this.name = "";
        this.email = "";
        this.phone = "";
        this.address = "";
    }

    // Requires: username, password, name, email, phone and address of user
    // Modifies: this
    // Effects: a constructor, sets all the user's fields to the arguments given
    //          this constructor is used when logging in
    public User(String username, String password, String name, String email, String phone, String address) {
        // Set fields to arguments given
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Requires: none
    // Modifies: "users.txt" file (adds User)
    // Effects: writes User's information to "users.txt"
    public void writeToFile() throws IOException {
        // FileWriter and BufferedWriter to append to "users.txt"
        FileWriter fw = new FileWriter("users.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        // Write each field on its own line
        bw.write(username + "\r");
        bw.write(password + "\r");
        bw.write(name + "\r");
        bw.write(email + "\r");
        bw.write(phone + "\r");
        bw.write(address + "\r");

        // Close the BufferedWriter
        bw.close();
    }

    // Requires: password, name, email, phone and address cannot be empty
    // Modifies: this
    //           "users.txt" (edits user within)
    // Effects: edits user's information and their information in "users.txt"
    public void editFile(String password, String name, String email, String phone, String address)
            throws IOException {
        // ArrayList that will hold the current lines inside of "users.txt"
        ArrayList<String> lines = new ArrayList<>();

        // integer to determine what the current line being read is
        int currentLine = 0;
        // boolean to determine if the current line should be skipped
        boolean shouldSkip = false;

        // FileReader and BufferedReader to read from "users.txt"
        FileReader fr = new FileReader("users.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read "users.txt"
        String line;
        while ((line = br.readLine()) != null) {
            // If the currentLine is a multiple of 6 (meaning the current line is a username)
            if (currentLine % 6 == 0) {
                // If the current line is the same as this username set shouldSkip to true, otherwise set it to false
                shouldSkip = line.equals(this.username);
            }
            // If the line should not be skipped
            if (!shouldSkip) {
                // Add the line to lines
                lines.add(line + "\r");
            }
            // Add 1 to the currentLine
            currentLine++;
        }

        // Close the BufferedReader
        br.close();

        // Change fields to entered information
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;

        // Add the new users information to lines, which will be written to "users.txt"
        lines.add(username + "\r"); // username cannot be changed, but it is rewritten with the new user
        lines.add(password + "\r");
        lines.add(name + "\r");
        lines.add(email + "\r");
        lines.add(phone + "\r");
        lines.add(address + "\r");

        // FileWriter and BufferedWriter to rewrite "users.txt" with the changed user
        FileWriter fw = new FileWriter("users.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        // Write every line in lines to "users.txt" (This will write every user, with the changed one at the end)
        for (String l : lines) {
            bw.write(l);
        }

        // Close the BufferedWriter
        bw.close();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
