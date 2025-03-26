package sample.friendsbook;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Friend {
    // Create all the fields for the Friend class
    private final String firstName;
    private final String lastName;
    private final String nickname;
    private String birthMonth;
    private final String birthDay;
    private final String birthYear;
    private final String phone;
    private final String email;
    private final String work;
    private final String friendGroup;
    private final String notes;

    // Requires: firstName must not be null or empty
    // Modifies: this
    // Effects: initializes a Friend object with the given arguments
    Friend( String firstName, String lastName, String nickname, int birthMonth,
            int birthDay,int birthYear, String phone, String email, String work, String friendGroup, String notes) {
        // Set fields to values entered
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        // Set birthMonth to the name of the month
        // Create an array of all the months
        String[] months = { "", "January", "February", "March", "April", "May", "June", "July",
                            "August", "September", "October", "November", "December"};
        // For each possible month number
        for (int i = 0; i < 13; i++) {
            // Check if the month is that number
            if (birthMonth == i) {
                // If it is, set this.birthMonth to the corresponding month
                this.birthMonth = months[i];
            }
        }
        // If birthDay is 0
        if (birthDay == 0) {
            // Set it as empty
            this.birthDay = "";
        }
        // Otherwise
        else {
            // Set it as the value given
            this.birthDay = String.valueOf(birthDay);
        }
       // If birthYear is 0
        if (birthYear == 0) {
            // Set it as empty
            this.birthYear = "";
        }
        // Otherwise
        else {
            // Set it as the value given
            this.birthYear = String.valueOf(birthYear);
        }
        // Set remaining fields to values entered
        this.phone = phone;
        this.email = email;
        this.work = work;
        this.friendGroup = friendGroup;
        this.notes = notes;
    }

    // Requires: fileName must end in ".txt" and be a valid file
    // Modifies: file with the name fileName (adds Friend to it)
    // Effects: writes Friend information into chosen file
    public void writeToFile(String fileName) throws IOException{
        // Create a FileWriter and BufferedWriter to write text into the chosen fileName
        // Anything written will also be appended because the second argument in FileWriter is true
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        // Now write each field in friends.txt, with a comma and carriage return after each field and a semicolon with a
        // carriage return after the last field
        bw.write(firstName + ",\r");
        bw.write(lastName + ",\r");
        bw.write(nickname + ",\r");
        bw.write(birthMonth + ",\r");
        bw.write(birthDay + ",\r");
        bw.write(birthYear + ",\r");
        bw.write(phone + ",\r");
        bw.write(email + ",\r");
        bw.write(work + ",\r");
        bw.write(friendGroup + ",\r");
        bw.write(notes + "\r");
        bw.write(";\r");

        // Close the BufferedWriter
        bw.close();
    }

    // Requires: none
    // Modifies: none
    // Effects: returns the full name of the Friend (first name followed by last name)
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getWork() {
        return work;
    }

    public String getFriendGroup() {
        return friendGroup;
    }

    public String getNotes() {
        return notes;
    }
}
