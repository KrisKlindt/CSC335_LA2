package model;

public class User {
	private String userName;
	private String hashedPassword;
	private String salt; // can change depending on what the salt is
	private LibraryModel LM;

	// Used when first creating a user
	public User(String uName, String password) {
		
	}
	
	// Used when starting up View, so that existing users can be created again
	public User(String uName, String hashedPassword, String a) {
		
	}
	
	public String getSalt() {
		return salt;
	}
	
	public String getUName() {
		return userName;
	}
}
