import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.finalproject.Entry;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EntryTests {
    // FileReader and BufferedReader
    FileReader fr;
    BufferedReader br;
    // FileWriter and BufferedWriter
    FileWriter fw;
    BufferedWriter bw;
    // ArrayList of lines currently in "Test entries.txt" and "Test2 entries.txt"
    ArrayList<String> currentLinesTest;
    ArrayList<String> currentLinesTest2;

    @Before
    public void setup() throws IOException {
        // Setup FileReader and BufferedReader to read from "Test entries.txt"
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Initialize currentLines as an empty ArrayList
        currentLinesTest = new ArrayList<>();

        // Read through users.txt
        String line;
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

        // Setup FileWriter and BufferedWriter to write to "Test entries.txt"
        fw = new FileWriter("Test entries.txt");
        bw = new BufferedWriter(fw);
        // By just opening a new FileWriter, "Test entries.txt" is fully emptied

        // Close the BufferedWriter
        bw.close();

        // Reopen FileWriter and BufferedWriter to write to "Test2 entries.txt"
        fw = new FileWriter("Test2 entries.txt");
        bw = new BufferedWriter(fw);
        // By just opening a new FileWriter, "Test2 entries.txt" is fully emptied

        // Close the BufferedWriter
        bw.close();
    }

    // Test the writeToFile method
    @Test
    public void testWriteToFile() throws IOException {
        // Three entries to add txt files
        Entry entry1 = new Entry("Test", "Grocery List:", "-ham\r-bacon\r-cheese");
        Entry entry2 = new Entry("Test", "To D0!!!", "EAT LOTS OF FOOD\rSleepRepeat");
        Entry entry3 = new Entry("Test2", "Untitled", "hello");

        // Initialize ArrayLists that will hold the lines in different entries.txt files
        ArrayList<String> lines1 = new ArrayList<>();
        ArrayList<String> lines2 = new ArrayList<>();
        ArrayList<String> lines3 = new ArrayList<>();
        ArrayList<String> lines4 = new ArrayList<>();

        // Create Arrays of the lines expected be in "Test entries.txt" and "Test2 entries.txt" at different times
        String[] expectedLines1 = {"Grocery List:", "5", "-ham", "-bacon", "-cheese"};
        String[] expectedLines2 = {
                "Grocery List:", "5", "-ham", "-bacon", "-cheese",
                "To D0!!!", "4", "EAT LOTS OF FOOD", "SleepRepeat"};
        String[] expectedLines3 = {
                "Untitled", "3", "hello"};

        // Confirm that "Test entries.txt" and "Test2 entries.txt" are empty by attempting to read from them
        // Reopen the BufferedReader
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null) {
            assertEquals("", line);
        }

        // Reopen the BufferedReader
        br.close();
        fr = new FileReader("Test2 entries.txt");
        br = new BufferedReader(fr);

        while((line = br.readLine()) != null) {
            assertEquals("", line);
        }

        // Write entry1 to the file
        entry1.writeToFile();

        // Read "Test entries.txt" again with the new Entry added, adding each line to lines1
        while((line = br.readLine()) != null) {
            lines1.add(line);
        }
        // Iterate through lines1
        for (int i = 0; i < lines1.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines1
            assertEquals(expectedLines1[i], lines1.get(i));
        }

        // Reset the BufferedReader so it reads from the top again by closing it and reopening it
        br.close();
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Write entry2 to the file
        entry2.writeToFile();

        // Read "Test entries.txt" again with the new Entry added, adding each line to lines2
        while((line = br.readLine()) != null) {
            lines2.add(line);
        }
        // Iterate through lines2
        for (int i = 0; i < lines2.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines2
            assertEquals(expectedLines2[i], lines2.get(i));
        }

        // Reset the BufferedReader so it reads from the top again by closing it and reopening it
        br.close();
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Write entry3 to the file (However this will be written to "Test2 entries.txt"
        entry3.writeToFile();

        // Read "Test entries.txt" again with the new Entry added, adding each line to lines3
        while((line = br.readLine()) != null) {
            lines3.add(line);
        }
        // Iterate through lines3
        for (int i = 0; i < lines3.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines2
            assertEquals(expectedLines2[i], lines3.get(i));
        }

        // Reset the BufferedReader so it reads from "Test2 entries.txt" closing it and reopening it
        br.close();
        fr = new FileReader("Test2 entries.txt");
        br = new BufferedReader(fr);

        // Read "Test2 entries.txt" again with the new Entry added, adding each line to line4
        while((line = br.readLine()) != null) {
            lines4.add(line);
        }
        // Iterate through lines4
        for (int i = 0; i < lines4.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines3
            assertEquals(expectedLines3[i], lines4.get(i));
        }

        // Close the BufferedReader
        br.close();
    }

    // Test the editFile method
    @Test
    public void testEditFile() throws IOException {
        // Create three Entries that will be used in tests
        Entry entry1 = new Entry("Test", "Test", "This is a\rNEWEntry.");
        Entry entry2 = new Entry("Test2", "Test", "test1 test2test3");
        Entry entry3 = new Entry("Test", "Random Thoughts", "Which came first\rThe Chicken\ror\rThe Egg");

        // Reopen the BufferedReader
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Confirm that "Test entries.txt" and "Test2 entries.txt" are empty by attempting to read from them
        // Reopen the BufferedReader
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null) {
            assertEquals("", line);
        }

        // Reopen the BufferedReader
        br.close();
        fr = new FileReader("Test2 entries.txt");
        br = new BufferedReader(fr);

        while((line = br.readLine()) != null) {
            assertEquals("", line);
        }

        // Write each Entry to entries.txt
        entry1.writeToFile();
        entry2.writeToFile();
        entry3.writeToFile();

        // Initialize ArrayLists that will hold the lines in the text files
        ArrayList<String> lines1 = new ArrayList<>();
        ArrayList<String> lines2 = new ArrayList<>();
        ArrayList<String> lines3 = new ArrayList<>();

        // Create Arrays of what information should be in the text files at different times
        String[] expectedLines1 = {
                "Test", "4", "This is a", "NEWEntry.",
                "Random Thoughts", "5", "I think...", "", "THE EGG"};
        String[] expectedLines2 = {
                "Random Thoughts", "5", "I think...", "", "THE EGG",
                "Test", "5", "This is now an", "OLD", "Entry."};
        String[] expectedLines3 = {
                "Test", "3", "test1 test2test3"};

        // Reopen the BufferedReader
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Update entry3
        entry3.editFile("I think...\r\rTHE EGG");

        //Read "Test entries.txt" after it has been updated
        while((line = br.readLine()) != null) {
            lines1.add(line);
        }
        // Iterate through lines1
        for (int i = 0; i < lines1.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines1
            assertEquals(expectedLines1[i], lines1.get(i));
        }

        // Reset the BufferedReader so it reads from the top again
        br.close();
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Update entry1
        entry1.editFile("This is now an\rOLD\rEntry.");

        //Read "Test entries.txt" after it has been updated
        while((line = br.readLine()) != null) {
            lines2.add(line);
        }
        // Iterate through lines2
        for (int i = 0; i < lines2.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines2
            assertEquals(expectedLines2[i], lines2.get(i));
        }

        // Reset the BufferedReader so it reads from "Test2 entries.txt"
        br.close();
        fr = new FileReader("Test2 entries.txt");
        br = new BufferedReader(fr);

        //Read "Test2 entries.txt" after it has been updated
        while((line = br.readLine()) != null) {
            lines3.add(line);
        }
        // Iterate through lines3
        for (int i = 0; i < lines3.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines3
            assertEquals(expectedLines3[i], lines3.get(i));
        }

        // Close the BufferedReader
        br.close();
    }

    // Test the removeFromFile method
    @Test
    public void testRemoveFromFile() throws IOException {
        // Create new entries
        Entry entry1 = new Entry("Test", "Unknown", "");
        Entry entry2 = new Entry("Test", "User1", "Unknown");
        Entry entry3 = new Entry("Test", "User2", "CHICKEN\r(Again)");

        // Write each entry
        entry1.writeToFile();
        entry2.writeToFile();
        entry3.writeToFile();

        // Remove entry2 from file
        entry2.removeFromFile();

        // Create an ArrayList to store the lines in Test entries.txt
        ArrayList<String> lines = new ArrayList<>();
        // Reopen the FileReader and BufferedReader to read from Test entries.txt
        fr = new FileReader("Test entries.txt");
        br = new BufferedReader(fr);

        // Read entries.txt
        String line;
        while((line = br.readLine()) != null) {
            // Add line to lines
            lines.add(line);
        }

        // Create an Array of expected lines in entries.txt
        ArrayList<String> expectedLines = new ArrayList<>();

        Collections.addAll(expectedLines,
                "Unknown", "3", "",
                "User2", "4", "CHICKEN", "(Again)");
        // Check that the contents of lines and expectedLines are the same
        assertEquals(expectedLines, lines);

        // Close the BufferedReader
        br.close();
    }

    // Test the toString method
    @Test
    public void testToString() {
        // Create new entries
        Entry entry1 = new Entry("", "Hello World!", "Hello World");
        Entry entry2 = new Entry("Hello12345???", "CH1CK3N", "I LIKE CHIIIICKENSSSS!!!!");

        // Create Strings that hold the values of the entries after using the toString method
        String string1 = entry1.toString();
        String string2 = entry2.toString();

        // Check that the strings contain what is expected
        assertEquals("Hello World!", string1);
        assertEquals("CH1CK3N", string2);
    }

    // Reset entries.txt back to its original contents
    @After
    public void cleanup() throws IOException{
        // Reopen the FileWriter and BufferedWriter to clear the document
        fw = new FileWriter("Test entries.txt");
        bw = new BufferedWriter(fw);

        // Write previous information into "Test entries.txt"
        // For line in currentLines
        for (String line : currentLinesTest) {
            // Write the line to "Test entries.txt"
            bw.write(line);
        }

        // Close the BufferedWriter
        bw.close();

        // Reopen the FileWriter and BufferedWriter to clear the document
        fw = new FileWriter("Test2 entries.txt");
        bw = new BufferedWriter(fw);

        // Write previous information into "Test2 entries.txt"
        // For line in currentLines
        for (String line : currentLinesTest2) {
            // Write the line to "entries.txt
            bw.write(line);
        }

        // Close the BufferedWriter
        bw.close();
    }
}
