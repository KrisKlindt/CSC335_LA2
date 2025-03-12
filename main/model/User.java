package model;

import java.security.SecureRandom;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class User {
	private String userName;
	private String hashedPassword;
	private String saltString;
	private byte[] salt;
	private LibraryModel LM;

	// Used when first creating a user
	public User(String uName, String password) {
		this.userName = uName;
		
		byte [] s = generateSalt();
		this.salt = s;
		try {
			this.saltString = Base64.getEncoder().encodeToString(salt);
			this.hashedPassword = hashedToString(password, s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	// Used when starting up View, so that existing users can be created again
	// From existing user txt files and put into UserRegistry
	public User(String uName) {
		// Should only take username, hashedPassword, saltString, and salt from the txt file
		try {
			String fp = "./main/database/users/"+ uName +".txt";
			BufferedReader in = new BufferedReader(new FileReader(fp));
			
			String line = in.readLine();
            String[] l = line.split(",");
            
            this.userName = l[0];
            this.hashedPassword = l[1];
            this.saltString = l[2];
            this.salt = Base64.getDecoder().decode(saltString);
            
            in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
	
	private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return hashedPassword;
    }
	
	private String hashedToString(String password, byte[] s) throws NoSuchAlgorithmException {
        byte[] hashedPassword = hashPassword(password, s);
        byte[] saltAndHash = new byte[s.length + hashedPassword.length];
        System.arraycopy(s, 0, saltAndHash, 0, s.length);
        System.arraycopy(hashedPassword, 0, saltAndHash, s.length, hashedPassword.length);
        return Base64.getEncoder().encodeToString(saltAndHash);
    }
	
	public String getSaltString() {
		return saltString;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
	public String getUName() {
		return userName;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	// called when a user logs in in the View, so that we don't try to fill in each library
	// of each user in the registry, since only one will be logged in at a time
	public void fillLibraryFromTxt() {
		//TODO
	}
	
	// called in View when the user wants to save their data, i.e. when they exit
	public void saveUserToFile() {
		// Should write to the uName_Salt.txt everything in the current library of the user- TODO
	}
}
