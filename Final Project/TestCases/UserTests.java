import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.finalproject.User;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTests {
    // FileReader and BufferedReader
    FileReader fr;
    BufferedReader br;
    // FileWriter and BufferedWriter
    FileWriter fw;
    BufferedWriter bw;
    // ArrayList of lines currently in "users.txt"
    ArrayList<String> currentLines;

    @Before
    public void setup() throws IOException {
        // Setup FileReader and BufferedReader to read from "users.txt"
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Initialize currentLines as an empty ArrayList
        currentLines = new ArrayList<>();

        // Read through "users.txt"
        String line;
        while((line = br.readLine()) != null) {
            // Add each line with a carriage return to the currentLines ArrayList
            currentLines.add(line + "\r");
        }

        // Close the BufferedReader
        br.close();

        // Setup FileWriter and BufferedWriter to write to "users.txt"
        FileWriter fw = new FileWriter("users.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        // By just opening a new FileWriter, "users.txt" is fully emptied

        // Close the BufferedWriter
        bw.close();
    }

    // Test the writeToFile method
    @Test
    public void testWriteToFile() throws IOException{
        // Three users to add to "users.txt"
        User user1 = new User("John100", "AB12;", "John", "", "", "I don't have one");
        User user2 = new User("Bobberto", "sIllY 2000!!!", "Bob Smith", "BobMan@example.com", "123-456-7890", "1234, Broadway");
        User user3 = new User("1984", "Bobberto");

        // Initialize ArrayLists that will hold the lines in "users.txt"
        ArrayList<String> lines1 = new ArrayList<>();
        ArrayList<String> lines2 = new ArrayList<>();
        ArrayList<String> lines3 = new ArrayList<>();

        // Arrays of the lines expected be in "users.txt" at different times
        String[] expectedLines1 = {"John100", "AB12;", "John", "", "", "I don't have one"};
        String[] expectedLines2 = {
                "John100", "AB12;", "John", "", "", "I don't have one",
                "Bobberto", "sIllY 2000!!!", "Bob Smith", "BobMan@example.com", "123-456-7890", "1234, Broadway"};
        String[] expectedLines3 = {
                "John100", "AB12;", "John", "", "", "I don't have one",
                "Bobberto", "sIllY 2000!!!", "Bob Smith", "BobMan@example.com", "123-456-7890", "1234, Broadway",
                "1984", "Bobberto", "", "", "", ""};

        // Reopen the BufferedReader
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Confirm that "users.txt" is empty by attempting to read from it
        String line;
        while((line = br.readLine()) != null) {
            assertEquals("", line);
        }

        // Write user1 to the file
        user1.writeToFile();

        // Read "users.txt" again with the new user added, adding each line to lines1
        while((line = br.readLine()) != null) {
            lines1.add(line);
        }
        // Confirm the length of lines1 is the same as expectedLines1
        assertEquals(lines1.size(), expectedLines1.length);
        // Iterate through lines1
        for (int i = 0; i < lines1.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines1
            assertEquals(expectedLines1[i], lines1.get(i));
        }

        // Reset the BufferedReader so it reads from the top again by closing it and reopening it
        br.close();
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Write user2 to the file
        user2.writeToFile();

        // Read "users.txt" again with the new user added, adding each line to lines2
        while((line = br.readLine()) != null) {
            lines2.add(line);
        }
        // Confirm the length of lines2 is the same as expectedLines2
        assertEquals(lines2.size(), expectedLines2.length);
        // Iterate through lines2
        for (int i = 0; i < lines2.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines2
            assertEquals(expectedLines2[i], lines2.get(i));
        }

        // Reset the BufferedReader so it reads from the top again by closing it and reopening it
        br.close();
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Write user3 to the file
        user3.writeToFile();

        // Read "users.txt" again with the new user added, adding each line to lines3
        while((line = br.readLine()) != null) {
            lines3.add(line);
        }
        // Confirm the length of lines3 is the same as expectedLines3
        assertEquals(lines3.size(), expectedLines3.length);
        // Iterate through lines3
        for (int i = 0; i < lines3.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines3
            assertEquals(expectedLines3[i], lines3.get(i));
        }

        // Close the BufferedReader
        br.close();
    }

    // Test the editFile method
    @Test
    public void testEditFile() throws IOException {
        // Three users that will be used in tests
        User user1 = new User("Jacob", "AB12;", "John", "123@sillystreet.com", "", "I don't have one");
        User user2 = new User("Bobberto", "sIllY 2000!!!", "Bob Smith", "BobMan@example.com", "123-456-7890", "1234, Broadway");
        User user3 = new User("1984", "Bobberto", "IMAGINE", "", "", "I live alone!");

        // Reopen the BufferedReader
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Confirm that "users.txt" is empty by attempting to read from it
        String line;
        while((line = br.readLine()) != null) {
            assertEquals("", line);
        }

        // Write each user to "users.txt"
        user1.writeToFile();
        user2.writeToFile();
        user3.writeToFile();

        // Initialize ArrayLists that will hold the lines in "users.txt"
        ArrayList<String> lines1 = new ArrayList<>();
        ArrayList<String> lines2 = new ArrayList<>();

        // Arrays of what information should be in "users.txt" at different times
        String[] expectedLines1 = {
                "Jacob", "AB12;", "John", "123@sillystreet.com", "", "I don't have one",
                "Bobberto", "sIllY 2000!!!", "Bob Smith", "BobMan@example.com", "123-456-7890", "1234, Broadway",
                "1984", "Bobberto", "", "ILOVE1984@itsogood.com", "1984001984", ""};
        String[] expectedLines2 = {
                "Bobberto", "sIllY 2000!!!", "Bob Smith", "BobMan@example.com", "123-456-7890", "1234, Broadway",
                "1984", "Bobberto", "", "ILOVE1984@itsogood.com", "1984001984", "",
                "Jacob", "AB12;", "CHICKEN", "", "", "The Chicken Coop"};

        // Update user3
        user3.editFile("Bobberto", "", "ILOVE1984@itsogood.com", "1984001984", "");

        //Read "users.txt" after it has been updated
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
        fr = new FileReader("users.txt");
        br = new BufferedReader(fr);

        // Update user1
        user1.editFile("AB12;", "CHICKEN", "", "", "The Chicken Coop");

        //Read users.txt after it has been updated
        while((line = br.readLine()) != null) {
            lines2.add(line);
        }
        // Iterate through lines2
        for (int i = 0; i < lines2.size(); i++) {
            // Confirm the current line is equal to its expected result in expectedLines2
            assertEquals(expectedLines2[i], lines2.get(i));
        }

        // Close the BufferedReader
        br.close();
    }

    // Reset "users.txt" back to its original contents
    @After
    public void cleanup() throws IOException{
        // Reopen the FileWriter and BufferedWriter to clear the document
        fw = new FileWriter("users.txt");
        bw = new BufferedWriter(fw);

        // Write previous information into users.txt
        // For line in currentLines
        for (String line : currentLines) {
            // Write the line to users.txt
            bw.write(line);
        }

        // Close the BufferedWriter
        bw.close();
    }
}
