package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PlayListTest {
	PlayList pl = new PlayList("Playlist");
	
	@Test
	void getTitleTest() {
		assertEquals(pl.getTitle(), "Playlist");
	}
	
	@Test
	void getPlayListEmptyTest() {
		ArrayList<Song> p = pl.getPlayList();
		assertTrue(p.size() == 0);
	}
	
	@Test
	void addSongTest() {
		pl.addSong(new Song("a", "b", "c", "d"));
		ArrayList<Song> p = pl.getPlayList();
		assertTrue(p.size() == 1);
	}
	
	@Test
	void addToFrontTest() {
		pl.addToFront(new Song("a", "b", "c", "d"));
		pl.addToFront(new Song("yes", "a", "a", "a"));
		assertTrue(pl.getPlayList().getFirst().getTitle().equals("yes"));
	}
	
	@Test
	void removeSongTest() {
		Song s = new Song("a", "b", "c", "d");
		pl.addSong(s);
		pl.removeSong(s);
		ArrayList<Song> p = pl.getPlayList();
		assertTrue(p.size() == 0);
	}
	
	@Test
	void length10MoreThan10Test() {
		// will only need to work for the case that length = 11
		for (int i = 0; i < 10; i++) {
			String title = "song" + i;
			pl.addSong(new Song(title, "a", "a", "a"));
		}
		pl.length10();
		assertTrue(pl.getPlayList().size() == 10);
	}
	
	@Test
	void length10LessThan10Test() {
		for (int i = 0; i < 5; i++) {
			String title = "song" + i;
			pl.addSong(new Song(title, "a", "a", "a"));
		}
		pl.length10();
		assertTrue(pl.getPlayList().size() <= 10);
	}
	
	@Test
	void shuffleSongsTest() {
		for (int i = 0; i < 5; i++) {
			String title = "song" + i;
			pl.addSong(new Song(title, "a", "a", "a"));
		}
		pl.shuffleSongs();
	}
	
	@Test
	void equalsNullTest(){
		assertFalse(pl.equals(null));
	}
	
	@Test
	void equalsThisTest(){
		assertTrue(pl.equals(pl));
	}
	
	@Test
	void equalsDiffClassTest(){
		assertFalse(pl.equals(new Song("a", "b", "c", "d")));
	}
	
	@Test
	void equalsSameNameTest(){
		assertTrue(pl.equals(new PlayList("Playlist")));
	}
	
	@Test
	void printPlayListTest() {
		pl.addSong(new Song("a", "b", "c", "d"));
		pl.printPlaylist();
	}
}
