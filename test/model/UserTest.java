package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import database.MusicStore;

public class UserTest {
	
	static MusicStore ms = new MusicStore();

	@Test 
	void createUserTest(){
		User user = new User("icota", "HELLOworld");
		assertEquals("icota",user.getUName());
		assertNotNull(user.getHashedPassword());
		
	}
	
	@Test
	void fillLibraryTest() {
		User dTrump = new User("Donald_Trump");
		dTrump.fillLibraryFromTxt();
		assertEquals(dTrump.library.getPlayLists().getFirst(), "Favorite Songs");
		assertEquals(dTrump.library.getAlbumTitles().getFirst(), "Waking Up");
	}
	
	@Test
	void getUNameTest() {
		User dTrump = new User("Donald_Trump");
		assertEquals(dTrump.getUName(), "Donald_Trump");
	}
	
	@Test
	void getHashedPasswordTest() {
		User dTrump = new User("Donald_Trump");
		assertEquals(dTrump.getHashedPassword(), "vwLPMSRY9EQoy5ms+iHNwF5qQRkrsbFCBtC0Ev7vjAOfZh0EHSEvHCQ4ntqDP2JV");
	}
	
	@Test
	void getSaltStringTest() {
		User dTrump = new User("Donald_Trump");
		assertEquals(dTrump.getSaltString(), "vwLPMSRY9EQoy5ms+iHNwA==");
	}
	
	@Test
	void saveUserTest() {
		User jBiden = new User("Joe_Biden");
		ArrayList<Album> albs = ms.searchAlbumByTitle("21", false);
		jBiden.library.addAlbum(albs.getFirst());
		for (Song s : albs.getFirst().getAlbum()) {
			jBiden.library.addSong(s);
		}
		jBiden.library.createPlayList("dem");
		PlayList pl = jBiden.library.searchPlayList("dem");
		ArrayList<Song> song = jBiden.library.searchSongByTitle("Turning Tables");
		jBiden.library.addSongToPlayList(pl, song.getFirst());
		jBiden.saveUserToFile();
		User jBiden2 = new User("Joe_Biden");
		jBiden2.fillLibraryFromTxt();
		assertEquals(jBiden2.library.getAlbumTitles().getFirst(), "21");
		ArrayList<Song> song2 = jBiden2.library.searchSongByTitle("Turning Tables");
		assertEquals(song2.getFirst().getTitle(), "Turning Tables");
	}
}
