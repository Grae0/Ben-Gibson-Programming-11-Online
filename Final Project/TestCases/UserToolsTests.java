import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.finalproject.Entry;
import sample.finalproject.User;
import sample.finalproject.UserTools;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserToolsTests {
    // FileReader and BufferedReader
    FileReader fr;
    BufferedReader br;
    // FileWriter and BufferedWriter
    FileWriter fw;
    BufferedWriter bw;
    // ArrayList of lines currently in "users.txt", "Test entries.txt" and "Test2 entries.txt"
    ArrayList<String> currentLinesUsers;
    ArrayList<String> currentLinesTest;
    ArrayList<String> currentLinesTest2;

    @Before
    public void setup() throws IOException {
        // Setup FileReader and BufferedReader to read from "users.txt"
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Initialize currentLines as an empty ArrayList
        currentLinesUsers = new ArrayList<>();

        // Read through users.txt
        String line;
        while((line = br.readLine()) != null) {
            // Add each line with a carriage return to the currentLines ArrayList
            currentLinesUsers.add(line + "\r");
        }

        // Close the BufferedReader
        br.close();

        // Setup FileReader and BufferedReader to read from "Test entries.txt"
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Initialize currentLines as an empty ArrayList
        currentLinesTest = new ArrayList<>();

        // Read through users.txt
        while((line = br.readLine()) != null) {
            // Add each line with a carriage return to the currentLines ArrayList
            currentLinesTest.add(line + "\r");
        }

        // Close the BufferedReader
        br.close();

        // Setup FileReader and BufferedReader to read from "Test2 entries.txt"
        fr = new FileReader("Test2 entries.txt");
        br = new BufferedReader(fr);

        // Initialize currentLines as an empty ArrayList
        currentLinesTest2 = new ArrayList<>();

        // Read through Test2 entries.txt
        while((line = br.readLine()) != null) {
            // Add each line with a carriage return to the currentLines ArrayList
            currentLinesTest2.add(line + "\r");
        }

        // Close the BufferedReader
        br.close();

        // Setup FileWriter and BufferedWriter to write to "users.txt"
        fw = new FileWriter("users.txt");
        bw = new BufferedWriter(fw);
        // By just opening a new FileWriter, "users.txt" is fully emptied

        // Close the BufferedWriter
        bw.close();

        // Reopen FileWriter and BufferedWriter to write to "Test entries.txt"
        fw = new FileWriter("Test entries.txt");
        bw = new BufferedWriter(fw);
        // By just opening a new FileWriter, "Test entries.txt" is fully emptied

        // Close the BufferedWriter
        bw.close();

        // Setup FileWriter and BufferedWriter to write to "Test2 entries.txt"
        fw = new FileWriter("Test2 entries.txt");
        bw = new BufferedWriter(fw);
        // By just opening a new FileWriter, "Test2 entries.txt" is fully emptied

        // Close the BufferedWriter
        bw.close();
    }

    // Test the getUsernames method
    @Test
    public void testGetUsernames() throws IOException {
        // Create Users to add to users.txt
        User user1 = new User("Bob1000^!!", "KINGBOB!");
        User user2 = new User("$$$7862$$$", "Johnny123", "John", "john12@example.com", "000-000-0000", "none");
        User user3 = new User(":||HELLO1991||:", "mynameiscool");

        // Write each user to users.txt
        user1.writeToFile();
        user2.writeToFile();
        user3.writeToFile();

        // Create an ArrayList of usernames in users.txt using the getUsernames method
        ArrayList<String> usernames = UserTools.getUsernames();
        // Create an Array of the expected usernames
        ArrayList<String> expectedUsernames = new ArrayList<>();
        // Add all the expected usernames to expectedUsernames
        expectedUsernames.add("Bob1000^!!");
        expectedUsernames.add("$$$7862$$$");
        expectedUsernames.add(":||HELLO1991||:");

        // Check that the usernames are equal to what is expected
        assertEquals(expectedUsernames, usernames);
    }

    // Test the getEntries method
    @Test
    public void testGetEntries() throws IOException {
        // Create three entries
        Entry entry1 = new Entry("Test", "Got to Wake up Early", "Set an alarm for\n\n1AM");
        Entry entry2 = new Entry("Test2", "Test", "TEST\ntest\nT\ne\ns\nt");
        Entry entry3 = new Entry("Test", "Math", "Section 1.1\nSection 1.3");

        // Write each Entry to entries.txt
        entry1.writeToFile();
        entry2.writeToFile();
        entry3.writeToFile();

        // Create ArrayLists of George's entries and What24's entries using the getEntries method
        ArrayList<Entry> georgeEntries = UserTools.getEntries("Test");
        ArrayList<Entry> what24Entries = UserTools.getEntries("Test2");

        // Create Arrays of the expected strings for each user
        String[] expectedGeorgeEntries = {"Got to Wake up Early", "5", "Set an alarm for\n\n1AM", "Math", "4", "Section 1.1\nSection 1.3"};
        String[] expectedWhat24Entries = {"Test", "8", "TEST\ntest\nT\ne\ns\nt"};

        // Counter for lines
        int counter = 0;

        // For Entry in georgeEntries
        for (Entry entry : georgeEntries) {
            // Increment counter after finding element in expectedGeorgeEntries
            assertEquals(entry.getTitle(), expectedGeorgeEntries[counter++]);
            assertEquals(Integer.toString(entry.getLength()), expectedGeorgeEntries[counter++]);
            assertEquals(entry.getContents(), expectedGeorgeEntries[counter++]);
        }

        // Reset counter
        counter = 0;

        // For Entry in expectedWhat24Entries
        for (Entry entry : what24Entries) {
            // Increment counter after finding element in expectedGeorgeEntries
            assertEquals(entry.getTitle(), expectedWhat24Entries[counter++]);
            assertEquals(Integer.toString(entry.getLength()), expectedWhat24Entries[counter++]);
            assertEquals(entry.getContents(), expectedWhat24Entries[counter++]);
        }
    }

    // Reset notes.txt back to its original contents
    @After
    public void cleanup() throws IOException{
        // Reopen the FileWriter and BufferedWriter to clear the document
        fw = new FileWriter("users.txt");
        bw = new BufferedWriter(fw);

        // Write previous information into notes.txt
        // For line in currentLines
        for (String line : currentLinesTest) {
            // Write the line to notes.txt
            bw.write(line);
        }

        // Close the BufferedWriter
        bw.close();

        // Reopen the FileWriter and BufferedWriter to clear the document
        fw = new FileWriter("Test entries.txt");
        bw = new BufferedWriter(fw);

        // Write previous information into notes.txt
        // For line in currentLines
        for (String line : currentLinesTest) {
            // Write the line to notes.txt
            bw.write(line);
        }

        // Close the BufferedWriter
        bw.close();

        // Reopen the FileWriter and BufferedWriter to clear the document
        fw = new FileWriter("Test2 entries.txt");
        bw = new BufferedWriter(fw);

        // Write previous information into notes.txt
        // For line in currentLines
        for (String line : currentLinesTest2) {
            // Write the line to notes.txt
            bw.write(line);
        }

        // Close the BufferedWriter
        bw.close();
    }
}
