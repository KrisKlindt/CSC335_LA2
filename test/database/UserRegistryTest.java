package database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.User;

public class UserRegistryTest {

	UserRegistry uR = new UserRegistry();
	
	@Test
	void addUserandCheckIfUserExistsTrueTest(){
		uR.addUser(new User("Isaac_Cota", "helloWorld"));
		assertTrue(uR.checkIfUserExists("Isaac_Cota"));
	}
	
	@Test
	void checkIfUserExistsFalseTest() {
		assertFalse(uR.checkIfUserExists("John_Doe"));
	}
	
	@Test
	void getUserTest() {
		uR.addUser(new User("Isaac_Cota", "helloWorld"));
		User user = uR.getUser("Isaac_Cota");
		assertEquals(user.getUName(), "Isaac_Cota");
	}
	
}
