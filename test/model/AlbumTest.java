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
		Song song1 = new Song("one", "Joe Biden", "yes");
		album1.addSong(song1);
		assertTrue(album1.getAlbum().size() == 1);
	}

	@Test
	void falseSearchByTitleTest() {
		Song song2 = new Song("one", "Joe Biden", "yes");
		album1.addSong(song2);
		assertTrue(album1.searchByTitle("two").size() == 0);
	}
	
	@Test
	void trueSearchByTitleTest() {
		Song song3 = new Song("one", "Joe Biden", "yes");
		album1.addSong(song3);
		assertTrue(album1.searchByTitle("one").size() == 1);
	}
	
	@Test
	void printTitleAndArtistTest() {
		album1.printTitleAndArtist();
	}
	
	@Test
	void printAlbumDetailsTest() {
		Song song4 = new Song("a", "b", "c");
		album1.addSong(song4);
		album1.printAlbumDetails();
	}
}
