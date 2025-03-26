// Try not to edit files directly as it might throw errors when running program
// If you wish to delete a saved friend, you must make sure there are other Friends in their friend group
// If there are no other friends you must also delete the friend group from "friend groups.txt"

package sample.friendsbook;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.ArrayList;


public class Controller {
    // Scene Builder Generated fields
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtNickname;
    public TextField txtMonth;
    public TextField txtDay;
    public TextField txtYear;
    public TextField txtPhone;
    public TextField txtEmail;
    public TextField txtWork;
    public TextField txtFriendGroup;
    public TextArea txtNotes;
    public Button btnCreateFriend;
    public ListView<Friend> friendList = new ListView<>();
    public Label lblName;
    public Label lblNickname;
    public Label lblBirthday;
    public Label lblPhone;
    public Label lblEmail;
    public Label lblWork;
    public Label lblFriendGroup;
    public Label lblNotes;
    public Button btnDeleteFriend;
    public Button btnSaveFriends;
    public Button btnLoadFriends;
    public ListView<String> friendGroupList = new ListView<>();
    // Create integer array of three numbers that represent the three dates
    // They will be assigned in allowFriendCreation and used in createFriend
    private final int[] intDates = {0, 0, 0};
    // Create a String of the friend group file name that is to be loaded when load button is clicked
    String friendGroupChoice;


    // Requires: List of Friends tab to be clicked
    // Modifies: friendGroupList (clears and fills with friend groups in "friend groups.txt")
    // Effects: purpose is so that when program is loaded with saved information, friend groups can be selected and
    //          loaded immediately without having to create new friends
    //          runs every time the tab is changed, but the overall purpose is for the first time it is opened
    public void fillFriendGroups(Event event) throws IOException{
        // Clear friendGroupList before adding to it
        friendGroupList.getItems().clear();

        // Create a FileReader and BufferedReader to read "friends groups.txt" file
        FileReader fr = new FileReader("friend groups.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read file
        String line;
        while((line = br.readLine()) != null) { // While line being read does not equal null
            friendGroupList.getItems().add(line); // Add the line (A string of a friend group) to friendGroupList
        }

        // Close BufferedReader
        br.close();
    }

    // Requires: year is an integer
    //           month is an integer between 0 and 12
    //           day is an integer between 0 and 31, depending on the month
    // Modifies: this
    // Effects: returns true if the given date is valid and returns false if it is invalid
    public boolean checkDate(int year, int month, int day) {
        // Create arrays of months that contain 30 and 31 days
        int[] month30 = {4, 6, 9, 11};
        // 0 is included in month31 because it represents an unentered value
        int[] month31 = {0, 1, 3, 5, 7, 8, 10, 12};
        // Check if month30 contains month (meaning the month has 30 days)
        for (int m : month30) {
            if (m == month) {
                // If the day is greater than or equal to 0 and less than or equal to 30 the date is valid
                if (day >= 0 && day <= 30) {
                    // Return true signaling the date is valid
                    return true;
                }
            }
        }
        // Check if month 31 contains month (meaning the month has 31 days)
        for (int m : month31) {
            if (m == month) {
                // If the day is greater than or equal to 0 and less than or equal to 31 the date is valid
                if (day >= 0 && day <= 31) {
                    // Return true signaling the date is valid
                    return true;
                }
            }
        }
        // Check if the month is the only other month, February
        if (month == 2) {
            // If the year is divisible by 4 and not 100 or is divisible by 400 it is a leap year
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                // This means there are 29 days in February, meaning day must be less than or equal to 29
                // Return the boolean given when asking if day is greater than or equal to 0 and less than or equal to 29
                return day >= 0 && day <= 29;
            }
            // Otherwise it isn't a leap year meaning there are only 28 days in February
            else {
                // Return the boolean given when asking if day is greater than or equal to 0 and less than or equal to 28
                return day >= 0 && day <= 28;
            }
        }
        // If true has not been returned yet then the date given must be invalid
        return false;
    }

    // Requires: txtFirstName, txtDay, txtMonth, or txtYear to be altered
    // Modifies: the disabled state of btnCreateFriend
    // Effects: enables the btnCreateFriend depending on the text entered in the TextFields
    public void allowFriendCreation(KeyEvent keyEvent) {
        // btnCreateFriend will be enabled, but will go through many checks later in an attempt to be disabled
        btnCreateFriend.setDisable(false);
        // If txtFirstName is empty disable btnCreateFriend
        if (txtFirstName.getText().isEmpty()) {
            btnCreateFriend.setDisable(true);
        }
        // Otherwise txtFirstName contains something
        else {
            // Create an array of birthday TextFields
            String[] stringDates = {txtYear.getText(), txtMonth.getText(), txtDay.getText()};
            // Check the birthday TextFields to make sure they are either empty or integers
            for (int i = 0; i < 3; i++) {
                // If the current text field contains something
                if (!stringDates[i].isEmpty()) {
                    // Try to turn it into an integer
                    try {
                        // Assign the integer value to corresponding the intDates array
                        intDates[i] = Integer.parseInt(stringDates[i]);
                        // If the value is 0
                        if (intDates[i] == 0) {
                            // Disable btnCreateFriend
                            btnCreateFriend.setDisable(true);
                            // Break because we know the entire date is invalid
                            break;
                        }
                    }
                    // Otherwise the TextField does not contain an integer, so the button should be disabled
                    catch (NumberFormatException e) {
                        btnCreateFriend.setDisable(true);
                        break;
                    }
                }
                // If the TextField is empty
                else {
                    // Set the date to 0 (meaning empty)
                    intDates[i] = 0;
                }
            }
            // If the date is invalid
            // Note that 0 will be used for any date not entered
            if (!checkDate(intDates[0], intDates[1], intDates[2])) {
                // Disable btnCreateFriend
                btnCreateFriend.setDisable(true);
            }
        }
    }

    // Requires: btnCreateFriend to be clicked
    // Modifies: creates new Friend object with given information and adds it to FriendList to be displayed
    //           clears all TextFields
    //           disables btnCreateFriend
    // Effects: creates a new Friend with entered information that is displayed in List of Friends pane
    public void createFriend(ActionEvent actionEvent) {
        // Create variables to store information entered by user
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String nickname = txtNickname.getText();
        // Year, month and day TextFields were previously stored in intDates, which can now be accessed
        int year = intDates[0];
        int month = intDates[1];
        int day = intDates[2];
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String work = txtWork.getText();
        String notes = txtNotes.getText();

        // Friend group should have the first letter capitalized and the rest lower case
        // Create variables that will hold the name of the friend group, the first letter and the rest of the letters
        String friendGroup;
        String firstLetter;
        String restOfGroup = "";
        // If the user did not enter a friend group
        if (txtFriendGroup.getText().isEmpty()) {
            friendGroup = "Friends"; // Then the friend group should be "Friends"
        }
        else { // Otherwise the user did enter a friend group
            firstLetter = txtFriendGroup.getText().substring(0, 1).toUpperCase(); // Set the first letter to uppercase
            // However, they may not have entered more than one character, which we need to check
            if (txtFriendGroup.getText().length() > 1) { // If they did enter more than one character
                restOfGroup = txtFriendGroup.getText().substring(1).toLowerCase(); // Set the remaining characters to lower case
            }
            // note that if the user only entered one character restOfGroup was initialized as empty, so it will still concatenate
            friendGroup = firstLetter + restOfGroup; // Set friendGroup to full name entered
        }

        // Create new Friend object with newly created variables
        Friend friend = new Friend(firstName, lastName, nickname, month, day, year, phone, email, work, friendGroup, notes);
        // Create an array of all TextFields
        TextField[] texts = {txtFirstName, txtLastName, txtNickname, txtYear, txtMonth, txtDay, txtPhone, txtEmail, txtWork, txtFriendGroup};
        // Clear each TextField in the array
        for (TextField text : texts) {
            text.clear();
        }
        // Also clear the textArea
        txtNotes.clear();
        // Add friend to friendList
        friendList.getItems().add(friend);
        // Disable btnCreateFriend
        btnCreateFriend.setDisable(true);
        // Enable btnSaveFriends
        btnSaveFriends.setDisable(false);
    }

    // Requires: one of the Friends in friendList to be clicked
    // Modifies: sets lblName, lblNickname, lblBirthday, lblPhone, lblEmail, lblWork, lblFriendGroup and lblNotes to the
    //           corresponding fields of the clicked Friend
    //           enables btnDeleteFriend
    // Effects: displays the information of the selected Friend and allows for it to be deleted with a button
    public void displayFriend(MouseEvent mouseEvent) {
        // If selected Friend is not empty (The user did not select an empty area)
        if (friendList.getSelectionModel().getSelectedItem() != null) {
            // Create a friend with the same fields as the selected friend
            Friend friend = friendList.getSelectionModel().getSelectedItem();
            // Set the labels to the fields of the newly created friend
            lblName.setText(friend.getFirstName() + " " + friend.getLastName());
            lblNickname.setText(friend.getNickname());
            // Now to format the birthday
            // Create variables for month, day and year
            String month = friend.getBirthMonth();
            String day = friend.getBirthDay();
            String year = friend.getBirthYear();
            // If the user entered a month and day
            if (!month.isEmpty() && !day.isEmpty()) {
                // Add a space between them
                month += " ";
            }
            // If the user entered (a month or a day) and a year
            if ((!month.isEmpty() || !day.isEmpty()) && !year.isEmpty()) {
                // Add a comma between them
                year = ", " + year;
            }
            // Now lblBirthday is set to the birthday entered
            lblBirthday.setText(month + day + year);
            // Set the remaining labels to their values
            lblPhone.setText(friend.getPhone());
            lblEmail.setText(friend.getEmail());
            lblWork.setText(friend.getWork());
            lblFriendGroup.setText(friend.getFriendGroup());
            lblNotes.setText(friend.getNotes());
            // Enable the delete friend button
            btnDeleteFriend.setDisable(false);
        }
    }

    // Requires: btnDeleteFriend to be clicked
    // Modifies: removes selected Friend from friendList
    //           set lblName to 'Friend'
    //           set all other labels to empty
    //           disables btnDeleteFriend
    // Effects: deletes selected Friend from the display and if friendList is empty disable save button
    public void deleteFriend(ActionEvent actionEvent) {
        // Assign selected Friend to a variable
        Friend friend = friendList.getSelectionModel().getSelectedItem();
        // Remove the selected item from friends
        friendList.getItems().remove(friend);
        // Set lblName to 'Friend'
        lblName.setText("Friend");
        // Create an array of labels and iterate through them
        Label[] labels = {lblNickname, lblBirthday, lblPhone, lblEmail, lblWork, lblFriendGroup, lblNotes};
        for (Label label : labels) {
            // Empty each label
            label.setText("");
        }
        // Disable the delete Friend button
        btnDeleteFriend.setDisable(true);

        // Check if friendList is empty
        if (friendList.getItems().isEmpty()) {
            // Disable btnSaveFriends
            btnSaveFriends.setDisable(true);
        }
    }

    // Requires: btnSaveFriends to be clicked
    // Modifies: files of Friends in friendList
    //           friendList is cleared
    //           sets all friend labels to be empty
    //           "friend groups.txt" (all Friend's in friendList friend groups are added unless already there)
    // Effects: writes each Friend to their friend group's file if not already entered
    //          writes each Friend's friend group to "friend groups.txt" unless already there
    //          clears friendList
    public void saveFriends(ActionEvent actionEvent) throws IOException {
        // Create an ObservableList of items in friendList, really just an ArrayList of all friends
        ObservableList<Friend> friendsObservableList = friendList.getItems();
        // Create an Array of Friends already in file
        ArrayList<String> currentFriends = new ArrayList<>();

        // Iterate through ObservableList of friends
        for(Friend friend : friendsObservableList) {
            // Create variables that will represent the friend group and file name of the current friend
            String friendGroup = friend.getFriendGroup();
            String fileName = friendGroup.toLowerCase() + ".txt";

            // Check if friend's file exists
            File file = new File(fileName);

            // If file does not exist
            if (!file.exists()) {
                friend.writeToFile(fileName); // Create a new file with them as the first Friend

                // Create FileWriter and BufferedWriter
                FileWriter fw = new FileWriter("friend groups.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);

                // Add friend group to "friend groups.txt"
                bw.write(friendGroup + "\r");

                bw.close(); // Close the BufferedWriter

                friendGroupList.getItems().add(friendGroup); // Add the group to the friend group list
            }
            else {
                // Create a FileReader and BufferedReader to read friend's file
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);

                // Create a String Builder that will hold the person's full name
                StringBuilder fullName = new StringBuilder();

                // Read the file using the BufferedReader and check their full name
                String line;
                int linePos = 1;
                while ((line = br.readLine()) != null) {
                    if (linePos == 1) { // If the current line is the first line of the person (is their first name)
                        fullName = new StringBuilder(line.substring(0, line.length() - 1)); // Add firstName to the full Name
                        linePos += 1; // Add one to the line position
                    }
                    else if (linePos == 2) { // If the current line is the second line of the person (is their last name)
                        fullName.append(line, 0, line.length() - 1); // Add lastName to the full Name
                        linePos += 1; // Add one to the line position
                    }
                    else if (linePos == 12) { // If the line is the twelfth line of the person (is a semicolon)
                        currentFriends.add(fullName.toString()); // Add fullName to list of current Friends
                        linePos = 1; // Reset line position
                    }
                    else { // Otherwise it is at some other line position
                        linePos += 1; // Just keep going to the next line by adding one to linePos
                    }
                }
                // Now currentFriends contains all the names of each Friend in the current friend's friend group
                // This means if the Friend being created may already exist in a separate friend group but will still be created

                // Close the BufferedReader
                br.close();

                String friendFullName = friend.getFirstName() + friend.getLastName(); // Set the friend's fullName to their first and last
                // As long as the friend is new, meaning their full name is not already in current friends
                if (!currentFriends.contains(friendFullName)) {
                    friend.writeToFile(fileName); // Write the friend to a file of the chosen friend group
                }

                // Clear the current Friends ArrayList for next friend
                currentFriends.clear();
            }
        }

        // Clear friendList
        friendList.getItems().clear();

        // Empty labels
        // Set lblName to 'Friend'
        lblName.setText("Friend");
        // Create an array of labels and iterate through them
        Label[] labels = {lblNickname, lblBirthday, lblPhone, lblEmail, lblWork, lblFriendGroup, lblNotes};
        for (Label label : labels) {
            // Empty each label
            label.setText("");
        }

        // Disable btnSaveFriends
        btnSaveFriends.setDisable(true);
    }

    // Requires: one of the friend groups in friendGroupList to be clicked
    // Modifies: this
    //           enables btnLoadFriends
    // Effects: sets friendGroupChoice to be the file that holds the selected friend groups file and enables load button
    public void allowFriendLoading(MouseEvent mouseEvent) {
        // Make sure the selected item is not nothing
        if (friendGroupList.getSelectionModel().getSelectedItem() != null) {
            // Set friendGroup to the file name
            friendGroupChoice = friendGroupList.getSelectionModel().getSelectedItem() + ".txt";

            // Enable load friends button
            btnLoadFriends.setDisable(false);
        }
    }

    // Requires: btnLoadFriends to be clicked
    // Modifies: friendList (adds each Friend from "friends.txt")
    // Effects: adds each saved Friend to friendList, displaying them
    //          disables load button after use
    public void loadFriends(ActionEvent actionEvent) throws IOException{
        // Clear friendList if anything was left over
        friendList.getItems().clear();

        // Create an ArrayList of friends found in the selected friend group
        ArrayList<Friend> friends = CreateFriend.createAllFriends(friendGroupChoice);

        // For each Friend in friend
        for(Friend friend : friends){
            friendList.getItems().add(friend); // Add the friend to friendList to be displayed
        }

        // Enable btnSaveFriends and disable btnLoadFriends
        btnSaveFriends.setDisable(false);
        btnLoadFriends.setDisable(true);
    }
}
