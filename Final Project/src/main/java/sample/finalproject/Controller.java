// NOTES:
// I created a new way to save information, without using the commas and semicolon
// I did this because I noticed if you typed a comma or semicolon into Friends Book, the code did not work properly
// Instead, I found a way to save the information, without separating it. This way, anything can be typed and the code
// will still work as intended.
// Hope you like it!

package sample.finalproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    // Create fields from sample.fxml
    public VBox welcomePage;
    public VBox signupPage;
    public TextField textSignupUsername;
    public TextField textSignupPassword;
    public TextField textSignupConfirm;
    public Label lblSignupInstructions;
    public VBox loginPage;
    public Label lblLoginInstructions;
    public TextField textLoginUsername;
    public TextField textLoginPassword;
    public TabPane mainPage;
    public VBox accountInformationPane;
    public Label lblUsername;
    public Label lblPassword;
    public Label lblName;
    public Label lblEmail;
    public Label lblPhone;
    public Label lblAddress;
    public VBox accountEditingPane;
    public TextField textAccountPassword;
    public TextField textAccountName;
    public TextField textAccountEmail;
    public TextField textAccountPhone;
    public TextField textAccountAddress;
    public Label lblEditInstructions;
    public Button btnEditInformation;
    public ListView<Entry> listEntries = new ListView<>();
    public Label lblEntryTitle;
    public TextField textEntryTitle;
    public TextArea textEntryContents;
    public Button btnSaveEntry;
    public Button btnDeleteEntry;
    // Create a variable to represent the current user's account
    User user;

    // Requires: Continue button to be clicked
    // Modifies: welcomePage (visibility)
    //           loginPage (visibility)
    //           signupPage (visibility)
    // Effects: moves from the welcome page to displaying either the signup or login page
    public void start(ActionEvent actionEvent) throws IOException {
        // Set welcome page to invisible
        welcomePage.setVisible(false);

        // Check whether to open the signup page or the login page
        // If at least one user exists
        if (UserTools.usersExist()) {
            // Set the login page to visible because at least one user has already been created
            loginPage.setVisible(true);
        }
        // Otherwise no users have been created yet
        else {
            // Set the signup page to visible because there are no created users
            signupPage.setVisible(true);
        }
    }

    // Requires: Confirm button to be clicked
    // Modifies: lblSignupInstructions (text)
    //           lblUsername (text)
    //           lblPassword (text)
    //           signupPage (visibility)
    //           mainPage (visibility)
    // Effects: if the text in the username, password and confirm password TextFields are valid, display the main page
    //          otherwise set instructions to tell user what is wrong with entered values
    public void signup(ActionEvent actionEvent) throws IOException {
        // Strings that hold the text in the signup TextFields
        String username = textSignupUsername.getText();
        String password = textSignupPassword.getText();
        String confirm = textSignupConfirm.getText();

        // If the information entered was valid
        // (By calling this, it will also change the instructions if the information is invalid)
        if (UserTools.signupIsValid(lblSignupInstructions, username, password, confirm)) {
            // New user with the given information
            user = new User(username, password);
            // Write the user to users.txt
            user.writeToFile();

            // Set labels in Account tab to entered information
            lblUsername.setText(username);
            lblPassword.setText(password);

            // Create a new file for journal entries (don't do anything with it yet though)
            FileWriter fw = new FileWriter(username + " entries.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.close();

            // Set signupPage to invisible and mainPage to visible
            signupPage.setVisible(false);
            mainPage.setVisible(true);
        }
    }

    // Requires: Login button to be clicked
    // Modifies: lblLoginInstructions (set text)
    //           lblUsername, lblPassword, lblName, lblEmail, lblPhone and lblAddress (Text)
    //           listEntries (adds Entries)
    //           loginPage (visibility)
    //           mainPage (visibility)
    // Effects: logs in to previous existing account if username and password match with those stored in "users.txt"
    public void login(ActionEvent actionEvent) throws IOException {
        // Strings that hold the text in the login TextFields
        String username = textLoginUsername.getText();
        String password = textLoginPassword.getText();

        // Get fields from user
        ArrayList<String> fields = UserTools.getFields(username);

        // If fields are empty (meaning no username was found)
        if (fields.isEmpty()) {
            lblLoginInstructions.setText("Username does not exist");
        }
        // Otherwise fields is not empty
        else {
            // If the password does not match the password found in fields
            if (!password.equals(fields.getFirst())) {
                lblLoginInstructions.setText("Password is incorrect");
            }
            // Otherwise the password is correct
            else {
                // Create a new User with the fields and set it to user
                user = new User(username, password, fields.get(1), fields.get(2), fields.get(3), fields.get(4));

                // Set labels in Account tab to fields
                lblUsername.setText(username);
                lblPassword.setText(password);
                lblName.setText(fields.get(1));
                lblEmail.setText(fields.get(2));
                lblPhone.setText(fields.get(3));
                lblAddress.setText(fields.get(4));

                // Get all entries of the user
                ArrayList<Entry> entries = UserTools.getEntries(username);

                // Add entries to listEntries
                for (Entry entry : entries) {
                    listEntries.getItems().add(entry);
                }

                // Set loginPage to invisible and mainPage to visible
                loginPage.setVisible(false);
                mainPage.setVisible(true);
            }
        }
    }

    // Requires: Login button on signup page to be clicked
    // Modifies: signupPage (visibility)
    //           loginPage (visibility)
    //           textSignupUsername, textSignupPassword, textSignupConfirmPassword and lblSignupInstructions (set to empty)
    // Effects: changes page from signup to login
    //          empties the TextFields and Label on the signup page
    public void goLogin(ActionEvent actionEvent) {
        // Set the signup page to invisible and the login page to visible
        signupPage.setVisible(false);
        loginPage.setVisible(true);

        // Clear TextFields and Label
        textSignupUsername.setText("");
        textSignupPassword.setText("");
        textSignupConfirm.setText("");
        lblSignupInstructions.setText("");
    }

    // Requires: Sign Up button on login page to be clicked
    // Modifies: loginPage (visibility)
    //           signupPage (visibility)
    //           textLoginUsername, textLoginPassword and lblLoginInstructions (set to empty)
    // Effects: changes page from login to signup
    //          empties the TextFields and Label on the login page
    public void goSignup(ActionEvent actionEvent) {
        // Set the login page to invisible and the signup page to visible
        loginPage.setVisible(false);
        signupPage.setVisible(true);

        // Clear TextFields and Label
        textLoginUsername.setText("");
        textLoginPassword.setText("");
        lblLoginInstructions.setText("");
    }

    // Requires: Edit Information button to be clicked
    // Modifies: accountInformationPane (visibility)
    //           accountEditingPane (visibility)
    //           btnEditInformation (set text)
    //           all Account TextFields (set text)
    //           all Account labels (set text)
    //           user (changes fields)
    //           "users.txt" (edits user within)
    // Effects: allows user to edit account information or confirm edited information, depending on when Edit Information button is clicked
    public void editInformation(ActionEvent actionEvent) throws IOException {
        // If edit information button has not been clicked and still reads 'Edit Information'
        if (btnEditInformation.getText().equals("Edit Information")) {
            // Set all textFields to hold their current Account values
            textAccountPassword.setText(lblPassword.getText());
            textAccountName.setText(lblName.getText());
            textAccountEmail.setText(lblEmail.getText());
            textAccountPhone.setText(lblPhone.getText());
            textAccountAddress.setText(lblAddress.getText());

            // Set information pane to invisible and editing pane to visible
            accountInformationPane.setVisible(false);
            accountEditingPane.setVisible(true);

            // Set button text to read 'Confirm'
            btnEditInformation.setText("Confirm");
        }
        // If edit information button has already been clicked and reads 'Confirm'
        else {
            // Create Strings for the fields and set them to the entered values
            String password = textAccountPassword.getText();
            String name = textAccountName.getText();
            String email = textAccountEmail.getText();
            String phone = textAccountPhone.getText();
            String address = textAccountAddress.getText();

            // Check if edit is valid
            if (UserTools.editIsValid(lblEditInstructions, password)) {
                // Clear edit instructions
                lblEditInstructions.setText("");

                // Edit the user in users.txt with the editFile method
                user.editFile(password, name, email, phone, address);

                // Clear all textFields
                textAccountPassword.clear();
                textAccountName.clear();
                textAccountEmail.clear();
                textAccountPhone.clear();
                textAccountAddress.clear();

                // Set labels in Account tab to entered information
                lblPassword.setText((user.getPassword()));
                lblName.setText(user.getName());
                lblEmail.setText(user.getEmail());
                lblPhone.setText(user.getPhone());
                lblAddress.setText(user.getAddress());

                // Set editing pane to invisible and information pane to visible
                accountEditingPane.setVisible(false);
                accountInformationPane.setVisible(true);
                // Also change button so that it now reads 'Edit Information'
                btnEditInformation.setText("Edit Information");
            }
        }
    }

    // Requires: New Entry button to be clicked
    // Modifies: lblEntryTitle (set to empty)
    //           textEntryTitle (visibility and set to empty)
    //           textEntryContents (visibility and set to empty)
    //           btnSaveEntry and btnDeleteEntry (disabled state)
    // Effects: allows for a new Entry to be created and saved
    public void createEntry(ActionEvent actionEvent) {
        // Empty lblEntryTitle, textEntryTitle and textEntryContents
        lblEntryTitle.setText("");
        textEntryTitle.setText("");
        textEntryContents.setText("");
        // Set textEntryTitle and textEntryContents to visible
        textEntryTitle.setVisible(true);
        textEntryContents.setVisible(true);

        // Disable btnSaveEntry and btnDeleteEntry
        btnSaveEntry.setDisable(true);
        btnDeleteEntry.setDisable(true);
    }

    // Requires: textEntryTitle or textEntryContents to be typed in
    // Modifies: btnSaveEntry (disabled state)
    //           btnDeleteEntry (disabled state)
    // Effects: checks if entry can be saved, and enables or disables the Save Entry button accordingly
    public void checkSaveEntry(KeyEvent actionEvent) throws IOException {
        // Create a list of entry titles
        ArrayList<Entry> entries = UserTools.getEntries(user.getUsername());

        boolean shouldDisable = false;

        // If the textEntryTitle is visible
        if (textEntryTitle.isVisible()) {
            if (textEntryTitle.getText().isEmpty()) {
                shouldDisable = true;
            }
            for (Entry entry : entries) {
                // If the entry title is the current title
                if (entry.getTitle().equals(textEntryTitle.getText())) {
                    shouldDisable = true;
                }
            }
        }

        // Disable or enable btnSaveEntry depending on value of shouldDisable
        btnSaveEntry.setDisable(shouldDisable);

        // Disables btnDeleteEntry
        btnDeleteEntry.setDisable(true);
    }

    // Requires: Save Entry button to be clicked
    // Modifies: lblEntryTitle (visibility and text)
    //           textEntryTitle (visibility and text)
    //           textEntryContents (visibility and text)
    //           Entry (creates a new object)
    //           listEntries (adds entry object)
    //           "(username) entries.txt" (saves the Entry to file)
    //           btnDeleteEntry (disabled state)
    // Effects: saves the Entry by creating an object and write its contents to "(username) entries.txt"
    public void saveEntry(ActionEvent actionEvent) throws IOException {
        // Create Strings to hold the text within the title and contents
        String title;
        // If textEntryTitle is visible
        if (textEntryTitle.isVisible()) {
            // Set title to the text
            title = textEntryTitle.getText();
        }
        // Otherwise textEntryTitle is invisible, meaning lblEntryTitle is
        else {
            // Set title to the label
            title = lblEntryTitle.getText();
        }
        // Also set the contents
        String contents = textEntryContents.getText();

        // Create a new Entry object with the user's username as well as the text the user entered
        Entry entry = new Entry(user.getUsername(), title, contents);

        // Create a boolean to represent whether the entry is new or not (has a new title)
        boolean isNew = true;

        // Create an ObservableList of Entries in listEntries
        ObservableList<Entry> myList = listEntries.getItems();
        // For each entry in the list of entries
        for (Entry e : myList) {
            // If the current title is the same as the entry title
            if (title.equals(e.getTitle())) {
                // Edit the entry
                e.editFile(contents);
                // Set isNew to false
                isNew = false;
                break;
            }
        }

        // If the item is new
        if (isNew) {
            // Add entry to listEntries
            listEntries.getItems().add(entry);

            // Save the Entry to "(username) entries.txt"
            entry.writeToFile();
        }

        // Set textEntryTitle, lblEntryTitle and textEntryContents to invisible and empty them
        textEntryTitle.setVisible(false);
        textEntryTitle.setText("");
        lblEntryTitle.setVisible(false);
        lblEntryTitle.setText("");
        textEntryContents.setVisible(false);
        textEntryContents.setText("");

        // Disable btnDeleteEntry
        btnDeleteEntry.setDisable(true);
    }

    // Requires: item in listEntries to be clicked
    // Modifies: textEntryTitle (visibility)
    //           lblEntryTitle (visibility)
    //           textEntryContents (visibility and text)
    //           btnSaveEntry (disabled state)
    //           btnDeleteEntry (disabled state)
    // Effects: when a entry in the list of entries is clicked on, its information is displayed to the user
    public void displayEntry(MouseEvent actionEvent) {
        // Set the entry to the selected item
        Entry entry = listEntries.getSelectionModel().getSelectedItem();

        // If entry is not null
        if (entry != null) {
            // Set textEntryTitle to invisible and textEntryContents to visible
            textEntryTitle.setVisible(false);
            textEntryContents.setVisible(true);

            // Set lblEntryTitle and textEntryContents to hold the information of the entry
            lblEntryTitle.setText(entry.getTitle());
            lblEntryTitle.setVisible(true);
            textEntryContents.setText(entry.getContents());

            // Disable Save entry button
            btnSaveEntry.setDisable(true);
            btnDeleteEntry.setDisable(false);
        }
    }

    // Requires: btnDeleteEntry to be clicked
    // Modifies: lblEntryTitle (set to empty)
    //           textEntryContents (set to empty and invisible)
    //           listEntries (removes entry)
    //           btnDeleteEntry (disabled state)
    //           "(username) entries.txt" (removes entry from "(username) entries.txt")
    // Effects: deletes selected entry from listEntries and "(username) entries.txt"
    public void deleteEntry(ActionEvent actionEvent) throws IOException {
        // Set lblEntryTitle to empty
        lblEntryTitle.setText("");

        // Set textEntryContents to empty and invisible
        textEntryContents.setText("");
        textEntryContents.setVisible(false);

        // Entry that holds the selected Entry
        Entry entry = listEntries.getSelectionModel().getSelectedItem();
        // Remove selected item from listEntries
        listEntries.getItems().remove(entry);

        // Remove entry from entries.txt
        entry.removeFromFile();

        // Disable btnDeleteEntry
        btnDeleteEntry.setDisable(true);
    }
}
