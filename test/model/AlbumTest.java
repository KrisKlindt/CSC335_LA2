package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlbumTest {
	Album album1 = new Album("title", "Donald Trump", "genre", "2025");
	
	@Test
	void getTitleTest() {
		assertEquals(album1.getTitle(), "title");
	}

	@Test
	void getArtistTest() {
		assertEquals(album1.getArtist(), "Donald Trump");
	}

	@Test
	void addSongTest() {
		Song song1 = new Song("one", "Joe Biden", "yes", "dem");
		album1.addSong(song1);
		assertTrue(album1.getAlbum().size() == 1);
	}
	
	void removeSongTest() {
		Song song1 = new Song("one", "Joe Biden", "yes", "dem");
		album1.addSong(song1);
		album1.removeSong(song1);
		assertTrue(album1.getAlbum().size() == 0);
	}

	@Test
	void falseSearchByTitleTest() {
		Song song2 = new Song("one", "Joe Biden", "yes", "dem");
		album1.addSong(song2);
		assertTrue(album1.searchByTitle("two").size() == 0);
	}
	
	@Test
	void trueSearchByTitleTest() {
		Song song3 = new Song("one", "Joe Biden", "yes", "dem");
		album1.addSong(song3);
		assertTrue(album1.searchByTitle("one").size() == 1);
	}
	
	@Test
	void printTitleAndArtistTest() {
		album1.printTitleAndArtist();
	}
	
	@Test
	void equalsNullTest(){
		assertFalse(album1.equals(null));
	}
	
	@Test
	void equalsThisTest(){
		assertTrue(album1.equals(album1));
	}
	
	@Test
	void equalsDiffClassTest(){
		assertFalse(album1.equals(new Song("a", "b", "c", "d")));
	}
	
	@Test
	void equalsSameInfoTest(){
		assertTrue(album1.equals(new Album("title", "Donald Trump", "genre", "2025")));
	}
	
	@Test
	void printAlbumDetailsTest() {
		Song song4 = new Song("a", "b", "c", "dem");
		album1.addSong(song4);
		album1.printAlbumDetails();
	}
}
