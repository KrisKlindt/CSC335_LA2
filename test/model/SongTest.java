package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

class SongTest {
	private Song song1 = new Song("one", "Joe Biden", "yes");
	
	@Test
	void getTitleTest() {
		assertEquals(song1.getTitle(), "one");
	}

	@Test
	void getArtistTest() {
		assertEquals(song1.getArtist(), "Joe Biden");
	}
	
	@Test
	void getAlbumTest() {
		assertEquals(song1.getAlbum(), "yes");
	}
	
	@Test
	void getFavoriteTest() {
		assertFalse(song1.getFavorite());
	}
	
	@Test
	void markAsFavoriteTest() {
		song1.markAsFavorite();
		assertTrue(song1.getFavorite());
	}
	
	@Test
	void removeFavoriteTest() {
		song1.markAsFavorite();
		song1.removeFavorite();
		assertFalse(song1.getFavorite());
	}
	
	@Test
	void equalToFalseTest() {
		Song song2 = new Song("two", "Donald Trump", "no");
		assertFalse(song1.equalTo(song2));
	}
	
	@Test
	void equalToTrueTest() {
		Song song2 = new Song("one", "Joe Biden", "yes");
		assertTrue(song1.equalTo(song2));
	}
	
	@Test
	void equalToDifferentArtistTest() {
		Song song2 = new Song("one", "Donald Trump", "yes");
		assertFalse(song1.equalTo(song2));
	}
	
	@Test
	void equalToDifferentAlbumTest() {
		Song song2 = new Song("one", "Joe Biden", "no");
		assertFalse(song1.equalTo(song2));
	}
	
	@Test
	void rateSongTest() {
        song1.rateSong(5);
        assertTrue(song1.getFavorite());
	}
	
	@Test
	void rateSongNotFavorite() {
        song1.rateSong(1);
        assertFalse(song1.getFavorite());
	}
	
	@Test
	void printAllDetailsTest() {
		song1.printAllDetails();
	}
	
	// Not sure what or how to test for printAllDetails
}
