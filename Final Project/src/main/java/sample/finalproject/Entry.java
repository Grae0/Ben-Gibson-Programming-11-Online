package sample.finalproject;

import java.io.*;
import java.util.ArrayList;

public class Entry {
    // Fields for Entry
    private final String username;
    private final String title;
    private String contents;
    private int length;

    // Requires: username and title cannot be empty
    //           contents of Entry
    // Modifies: this
    // Effects: creates a new Entry
    public Entry(String username, String title, String contents) {
        // Assign given arguments to fields
        this.username = username;
        this.title = title;
        this.contents = contents;
        // length is how many lines the Entry takes up (It will be at least 3, 1 for the username, 1 for the length
        // itself and 1 for the first line in contents)
        length = 3;
        // Determine how many lines are in contents
        for (int i = 0; i < contents.length(); i++) {
            // If the index is a carriage return or new line escape sequence
            if (contents.charAt(i) == '\n' || contents.charAt(i) == '\r') {
                // Add 1 to length
                length++;
            }
        }
        // Now length will contain the number of lines this Entry takes up
    }

    // Requires: none
    // Modifies: "(this.username) entries.txt" (adds Entry)
    // Effects: writes the Entry to "(this.username) entries.txt"
    public void writeToFile() throws IOException {
        // FileWriter and BufferedWriter for writing
        FileWriter fw = new FileWriter(username + " entries.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        // Write each field on its own line with contents taking up multiple lines
        bw.write(title + "\r");
        bw.write(length + "\r");
        bw.write(contents + "\r");

        // Close the BufferedWriter
        bw.close();
    }

    // Requires: contents cannot be null
    // Modifies: "(this.username) entries.txt" (changes Entry)
    // Effects: edits the Entry within "(this.username) entries.txt"
    public void editFile(String contents) throws IOException {
        // Assign all entries except for this one to lines
        ArrayList<String> lines = otherEntries();

        // Change fields to entered information
        this.contents = contents;
        length = 3;
        // Determine how many lines are in contents
        for (int i = 0; i < contents.length(); i++) {
            // Check if the index is a carriage return escape sequence
            if (contents.charAt(i) == '\n' || contents.charAt(i) == '\r') {
                // Add 1 to length
                length++;
            }
        }

        // Add the new entry's information to lines, which will be written to "(this.username) entries.txt"
        lines.add(title + "\r");
        lines.add(length + "\r");
        lines.add(contents + "\r");

        // Create a FileWriter and BufferedWriter to rewrite "(this.username) entries.txt" with the changed Entry
        FileWriter fw = new FileWriter(username + " entries.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        // Write every line in lines to "(this.username) entries.txt" (This will write every Entry, with the changed one at the end)
        for (String l : lines) {
            bw.write(l);
        }

        // Close the BufferedWriter
        bw.close();
    }

    // Requires: none
    // Modifies: "(this.username) entries.txt" (removes this entry)
    // Effects: removes this entry and its information from "(this.username) entries.txt"
    public void removeFromFile() throws IOException {
        // Assign all entries except for this one to lines
        ArrayList<String> lines = otherEntries();

        // FileWriter and BufferedWriter to write to "(this.username) entries.txt"
        FileWriter fw = new FileWriter(username + " entries.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        // For line in lines
        for (String string : lines) {
            // Write the line to "(this.username) entries.txt"
            bw.write(string);
        }

        // Close BufferedWriter
        bw.close();
    }

    // Requires: none
    // Modifies: none
    // Effects: returns an a ArrayList of all lines in "(this.username) entries.txt", except for this entry's lines
    public ArrayList<String> otherEntries() throws IOException {
        // ArrayList to represent the lines in "(this.username) entries.txt"
        ArrayList<String> lines = new ArrayList<>();

        // integers to determine what the current line being read is
        int currentLine = 0;
        int currentLength = 2;
        // boolean that determines whether the current line should be removed
        boolean shouldRemove = false;

        // Create a FileReader and BufferedReader to read from "(this.username) entries.txt"
        FileReader fr = new FileReader(username + " entries.txt");
        BufferedReader br = new BufferedReader(fr);

        // Read "(this.username) entries.txt"
        String line;
        while((line = br.readLine()) != null) {
            // If the currentLine is 0 (meaning the current line is a title)
            if (currentLine == 0) {
                // If the current line is equal to this title set shouldRemove to true, otherwise set it to false
                shouldRemove = line.equals(title);
            }
            // If the currentLine is 1 (meaning the current line is a length)
            else if (currentLine == 1) {
                // Set the currentLength to the line
                currentLength = Integer.parseInt(line) - 1;
            }

            // If the line should be added
            if (!shouldRemove) {
                lines.add(line + "\r");
            }

            // If the currentLine is the length (meaning the current line is the last line in the Entry)
            if (currentLine == currentLength) {
                // Reset the currentLine to 0
                currentLine = 0;
                // Reset shouldRemove
                shouldRemove = false;

            }
            // Otherwise the currentLine is not the last line of the Entry
            else {
                // Add 1 to currentLine
                currentLine++;
            }
        }

        // Close the BufferedReader
        br.close();

        // Return the ArrayList of lines
        return lines;
    }

    // Requires: none
    // Modifies: none
    // Effects: returns string version of Entry
    @Override
    public String toString() {
        // Return the title of the Entry
        return title;
    }

    // Getters
    public int getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
