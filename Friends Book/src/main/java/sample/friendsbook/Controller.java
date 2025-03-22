package sample.friendsbook;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    public TextArea txtNotes;
    public Button btnCreateFriend;
    public ListView<Friend> friendList = new ListView<>();
    public Label lblName;
    public Label lblNickname;
    public Label lblBirthday;
    public Label lblPhone;
    public Label lblEmail;
    public Label lblWork;
    public Label lblNotes;
    public Button btnDeleteFriend;
    // Create integer array of three numbers that represent the three dates
    // They will be assigned in allowFriendCreation and used in createFriend
    private final int[] intDates = {0, 0, 0};


    // Requires: year is an integer
    //           month is an integer between 0 and 12
    //           day is an integer between 0 and 31, depending on the month
    // Modifies: this
    // Effects: Returns true if the given date is valid and returns false if it is invalid
    public boolean checkDate(int year, int month, int day) {
        // Create arrays of months that contain 30 and 31 days
        int[] month30 = {4, 6, 9, 11};
        // 0 is included in month31 because it represents an unentered value
        int[] month31 = {0, 1, 3, 5, 7, 8, 10, 12};
        // Check if month30 contains month (meaning the month has 30 days)
        for (int m: month30) {
            if (m == month) {
                // If the day is greater than or equal to 0 and less than or equal to 30 the date is valid
                if (day >= 0 && day <= 30) {
                    // Return true signaling the date is valid
                    return true;
                }
            }
        }
        // Check if month 31 contains month (meaning the month has 31 days)
        for (int m: month31) {
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
    // Modifies: The disabled state of btnCreateFriend
    // Effects: Enables the btnCreateFriend depending on the text entered in the TextFields
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
        // Create new Friend object with newly created variables
        Friend friend = new Friend(firstName, lastName, nickname, month, day, year, phone, email, work, notes);
        // Create an array of all TextFields
        TextField[] texts = {txtFirstName, txtLastName, txtNickname, txtYear, txtMonth, txtDay, txtPhone, txtEmail, txtWork};
        // Clear each TextField in the array
        for (TextField text: texts) {
            text.clear();
        }
        // Also clear the textArea
        txtNotes.clear();
        // Add friend to friendList
        friendList.getItems().add(friend);
        // Disable btnCreateFriend
        btnCreateFriend.setDisable(true);
    }

    // Requires: one of the Friends in friendList to be clicked
    // Modifies: sets lblName, lblNickname, lblBirthday, lblPhone, lblEmail, lblWork and lblNotes to the corresponding
    //           fields of the clicked Friend
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
            lblNotes.setText(friend.getNotes());
            // Enable the delete friend button so that they can be removed
            btnDeleteFriend.setDisable(false);
        }
    }

    // Requires: btnDeleteFriend to be clicked
    // Modifies: removes selected Friend from friendList
    //           set lblName to be 'Friend'
    //           set all other labels to be empty
    //           disables btnDeleteFriend
    // Effects: deletes selected Friend from the display
    public void deleteFriend(ActionEvent actionEvent) {
        // Assign selected Friend to a variable
        Friend selected = friendList.getSelectionModel().getSelectedItem();
        // Remove the selected item from friends (the ObservableList)
        friendList.getItems().remove(selected);
        // Set lblName to 'Friend'
        lblName.setText("Friend");
        // Create an array of labels and iterate through them
        Label[] labels = {lblNickname, lblBirthday, lblPhone, lblEmail, lblWork, lblNotes};
        for (Label label: labels) {
            // Empty each label
            label.setText("");
        }
        // Disable btnDeleteFriend
        btnDeleteFriend.setDisable(true);
    }
}
