package sample.friendsbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CreateFriend {
    // Create an ArrayList of Friends
    private static final ArrayList<Friend> friends = new ArrayList<>();

    // Requires: fileName must end in ".txt"
    // Modifies: this
    // Effects: reads a file of Friends and adds calls parseFriend for each Friend found, then returns friends
    public static ArrayList<Friend> createAllFriends(String fileName) throws IOException{
        // Initialize FileReader and Buffered reader to read from fileName
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        // Clear friends in case anything was previously stored
        friends.clear();

        // Start to read selected file
        String line;
        StringBuilder friendString = new StringBuilder();
        // While the line being read is not null (meaning we have not reached the end of the file)
        while((line = br.readLine()) != null){
            if(!line.equals(";")){ // If the line is not ";"
                friendString.append(line); // Add the current line to friendString
            }
            else{ // Else the line is ";" meaning we have reached the end of the current Friend
                parseFriend(friendString.toString()); // Calls parseFriend, which adds Friend to friends
                friendString = new StringBuilder(); // Reset friendString for next Friend
            }
        }

        // Close Buffered Reader
        br.close();

        return friends; // Return friends ArrayList, which will be changed in parseFriend
    }

    // Requires: string must be each a String of Friend field with a comma separating them
    // Modifies: this
    // Effects: adds a Friend to friends if it has not already been added
    private static void parseFriend(String string){
        // Create an array to hold the position of each comma in the array
        int[] positions = new int[10];
        // Create a variable for each field and set them
        String firstName;
        String lastName;
        String nickname;
        int birthMonth = 0;
        int birthDay = 0;
        int birthYear = 0;
        String phone;
        String email;
        String work;
        String friendGroup;
        String notes;

        // Iterate over each character in string
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == ','){ // If the current character is a comma
                for(int j = 0; j < positions.length; j++){ // Iterate through positions
                    if(positions[j] == 0){ // If the current spot is 0 (empty)
                        positions[j] = i; // Change it to the position the comma was found in
                        break; // Break out of iterating of positions
                    }
                }
            }
        }
        // Now each index of positions holds the index in which a comma appears in string

        // Set each variable to the section between commas it should be in
        // For example, first name is from the start to the first comma
        firstName = string.substring(0, positions[0]);
        lastName = string.substring(positions[0] + 1, positions[1]);
        nickname = string.substring(positions[1] + 1, positions[2]);
        // For integer values, they may be empty, which would throw an error when typecasting
        // So use try and catch to make sure no errors are thrown
        try{
            birthMonth = Integer.parseInt(string.substring(positions[2] + 1, positions[3]));
        }catch(NumberFormatException _){}
        try{
            birthDay = Integer.parseInt(string.substring(positions[3] + 1, positions[4]));
        }catch(NumberFormatException _){}
        try{
            birthYear = Integer.parseInt(string.substring(positions[4] + 1, positions[5]));
        }catch(NumberFormatException _){}
        // Continue setting variables
        phone = string.substring(positions[5] + 1, positions[6]);
        email = string.substring(positions[6] + 1, positions[7]);
        work = string.substring(positions[7] + 1, positions[8]);
        friendGroup = string.substring(positions[8] + 1, positions[9]);
        notes = string.substring(positions[9] + 1);

        // Create a Friend with information and add it to friends
        Friend newFriend = new Friend(firstName, lastName, nickname, birthMonth, birthDay, birthYear, phone, email, work, friendGroup, notes);
        friends.add(newFriend);
    }
}
