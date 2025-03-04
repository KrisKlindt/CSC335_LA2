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
		pl.addSong(new Song("a", "b", "c"));
		ArrayList<Song> p = pl.getPlayList();
		assertTrue(p.size() == 1);
	}
	
	@Test
	void removeSongTest() {
		Song s = new Song("a", "b", "c");
		pl.addSong(s);
		pl.removeSong(s);
		ArrayList<Song> p = pl.getPlayList();
		assertTrue(p.size() == 0);
	}
	
	@Test
	void printPlayListTest() {
		pl.addSong(new Song("a", "b", "c"));
		pl.printPlaylist();
	}
}
