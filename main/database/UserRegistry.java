package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Album;
import model.Song;
import model.User;

public class UserRegistry {
	// key String is the hashed password of the user
	private HashMap<String, User> Users;

	public UserRegistry() {
		Users = new HashMap<String, User>();
		
		// Go through users/users.txt to get existing user data, add to the UserRegistry
		try {
			String fp = "./main/database/users/users.txt";
			BufferedReader in = new BufferedReader(new FileReader(fp));
			
			ArrayList<String[]> users = new ArrayList<String[]>();
			String line;
			// Goes through users.txt to get name and hashed password of each user
            while ((line = in.readLine()) != null) {
                String[] l = line.split(",");
                users.add(l);
            }
            
            // for each user, create a User object
            for (String[] user: users) {
            	String uName = user[0];
            	String hashedPword = user[1];
            	
            	User u = new User(uName, hashedPword, "a");
            	
            	//String filepath = "./main/database/albums/" + uName + "_" + u.getSalt() + ".txt";
            	// save for use later
            	
            	Users.put(hashedPword, u);
            }
            
            in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Used when creating a new User
	public void addUser(User u) {
		try {
	        String folderPath = "./main/database/albums";
	        String fileName = u.getUName() + "_" + u.getSalt() + ".txt";
	        
	        File folder = new File(folderPath);
	        
	        // Check if the folder exists, if not, create it
	        if (!folder.exists()) {
	            folder.mkdirs();
	        }
	        
	        // Create a File object for the file
	        File file = new File(folder, fileName);
	        file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}
	
	public void searchUserByName() {
		
	}
}
