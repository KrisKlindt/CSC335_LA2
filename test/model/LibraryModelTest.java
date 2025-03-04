package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import database.MusicStore;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.Song;
import model.Album;
import model.PlayList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;

class LibraryModelTest {
	LibraryModel lm = new LibraryModel();
	LibraryModel lm2 = new LibraryModel();
	private final InputStream originalSystemIn = System.in;
	
	@AfterEach
	void restoreSystemInStream() {
	    System.setIn(originalSystemIn);
	}
	
	void initLM2() {
		String input = ("yes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm2.createPlayList("Sad");
		lm2.addSongToPlayList("Sad", "Lullaby");
	}
	
	@Test
	void testCreatePlayList() {
		lm.createPlayList("Mood");
		assertTrue(lm.getPlayLists().size() == 1);
		lm.createPlayList("Angst");
		assertTrue(lm.getPlayLists().size() == 2);
	}
	
	@Test
	void testCreatePlayListSameName() {
		lm.createPlayList("Angst");		
		lm.createPlayList("Angst");
		assertTrue(lm.getPlayLists().size() == 1);
	}
	
	@Test
	void testAddSongNotInStore() {
		lm.addSong("Letter To My 13 Year Old Self");
		assertTrue(lm.getSongTitles().size() == 0);
	}
	
	@Test
	void testAddSongSingle() {
		lm.addSong("Set Fire To The Rain");
		assertTrue(lm.getSongTitles().size() == 1);
		assertEquals(lm.getSongTitles().get(0), "Set Fire to the Rain");
	}
	
	@Test
	void testAddSongMultipleYes() {
		String input = ("yes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.addSong("Lullaby");
		assertTrue(lm.getSongTitles().size() == 2);
		assertEquals(lm.getSongTitles().get(0), "Lullaby");
		assertEquals(lm.getSongTitles().get(1), "Lullaby");

	}
	
	@Test
	void testAddSongMultipleNo() {
		String input = ("no\nOneRepublic\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.addSong("Lullaby");
		assertTrue(lm.getSongTitles().size() == 1);
		assertEquals(lm.getSongTitles().get(0), "Lullaby");
		assertEquals(lm.getArtists().get(0), "OneRepublic");

	}
	
	@Test
	void testAddSongMultipleNoInvalidArtist() {
		String input = ("no\nAdele\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.addSong("Lullaby");
		assertTrue(lm.getSongTitles().size() == 0);
	}
	
	@Test
	void testAddSongMultipleNotYesOrNo() {
		String input = ("maybe\nyes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.addSong("Lullaby");
		assertTrue(lm.getSongTitles().size() == 2);
	}
	
	@Test
	void testAddAlbum() {
		lm.addAlbum("Sons");
		assertTrue(lm.getAlbumTitles().size() == 1);
		assertEquals(lm.getAlbumTitles().get(0), "Sons");
	}
	
	@Test
	void testSearchPlayList() {
		lm.createPlayList("Melancholy");
		assertTrue(lm.searchPlayList("melancholy"));
	}
	
	@Test
	void testSearchPlayListFail() {
		lm.createPlayList("Melancholy");
		assertFalse(lm.searchPlayList("Goddess"));
	}
	
	@Test
	void testAddSongToPlayList() {
		lm.createPlayList("Sad");
		assertTrue(lm.addSongToPlayList("Sad", "Winter Winds"));
	}
	
	@Test
	void testAddSongToPlayListNotInStore() {
		lm.createPlayList("Sad");
		assertFalse(lm.addSongToPlayList("Sad", "Letter To My 13 Year Old Self"));
	}
	
	@Test
	void testAddSongToPlayListMultipleYes() {
		String input = ("yes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.createPlayList("Sad");
		assertTrue(lm.addSongToPlayList("Sad", "Lullaby"));
		assertTrue(lm.getSongTitles().size() == 2);
		assertEquals(lm.getSongTitles().get(0), "Lullaby");
	}
	
	@Test
	void testAddSongToPlayListMultipleNo() {
		String input = ("no\nOneRepublic\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.createPlayList("Sad");
		assertTrue(lm.addSongToPlayList("Sad", "Lullaby"));
		assertTrue(lm.getSongTitles().size() == 1);
		assertEquals(lm.getSongTitles().get(0), "Lullaby");
	}
	
	@Test
	void testAddSongToPlayListMultipleNoInvalidArtist() {
		String input = ("no\nLaufey\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.createPlayList("Sad");
		assertFalse(lm.addSongToPlayList("Sad", "Lullaby"));
		assertTrue(lm.getSongTitles().size() == 0);
	}
	
	@Test
	void testAddSongToPlayListMultipleNotYesOrNo() {
		String input = ("maybe\nyes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		lm.createPlayList("Sad");
		assertTrue(lm.addSongToPlayList("Sad", "Lullaby"));
		assertTrue(lm.getSongTitles().size() == 2);
		assertEquals(lm.getSongTitles().get(0), "Lullaby");
	}
	
	@Test
	void testAddSongToPlayListInvalidPlayList() {
		lm.createPlayList("sad");
		assertFalse(lm.addSongToPlayList("happy", "Winter Winds"));
	}
	
	@Test
	void testRemoveSongFromPlayList() {
		lm.createPlayList("Sad");
		lm.addSongToPlayList("Sad", "Winter Winds");
		assertTrue(lm.removeSongFromPlayList("Sad", "Winter Winds"));
	}
	
	@Test
	void testRemoveSongFromPlayListEmpty() {
		lm.createPlayList("Sad");
		assertFalse(lm.removeSongFromPlayList("Sad", "Winter Winds"));
	}
	
	@Test
	void testRemoveSongFromPlayListMultipleYes() {
		initLM2();
		String input = ("yes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		assertTrue(lm2.removeSongFromPlayList("Sad", "Lullaby"));
	}
	
	@Test
	void testRemoveSongFromPlayListMultipleNo() {
		initLM2();
		String input = ("no\nOneRepublic\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		assertTrue(lm2.removeSongFromPlayList("Sad", "Lullaby"));
	}
	
	@Test
	void testRemoveSongFromPlayListMultipleNoInvalidArtist() {
		initLM2();
		String input = ("no\nLaufey\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		assertFalse(lm2.removeSongFromPlayList("Sad", "Lullaby"));
	}
	
	@Test
	void testRemoveSongFromPlayListMultipleNotYesOrNo() {
		initLM2();
		String input = ("maybe\nyes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		assertTrue(lm2.removeSongFromPlayList("Sad", "Lullaby"));
	}
	
	@Test
	void testRemoveSongFromPlayListInvalidPlayList() {
		assertFalse(lm2.removeSongFromPlayList("Happy", "Lullaby"));
	}
	
	@Test
	void testSearchSongByTitleOne() {
		lm.addSong("I Feel The Earth Move");
		ArrayList<Song> songList = lm.searchSongByTitle("I Feel The Earth Move");
		assertTrue(songList.size() == 1);
		assertEquals(songList.get(0).getTitle(), "I Feel The Earth Move");
	}
	 
	@Test
	void testSearchBySongTitleMultiple() {
		initLM2();
		ArrayList<Song> songList = lm2.searchSongByTitle("Lullaby");
		assertTrue(songList.size() == 2);
		assertEquals(songList.get(0).getTitle(), "Lullaby");
		assertEquals(songList.get(1).getTitle(), "Lullaby");
	}
	
	@Test
	void testSearchBySongTitleNotInLibrary() {
		ArrayList<Song> songList = lm.searchSongByTitle("Not in Library");
		assertTrue(songList.size() == 0);
	}
	
	@Test
	void testSearchSongByArtist() {
		lm.addSong("Set Fire To The Rain");
		ArrayList<Song> songList = lm.searchSongByArtist("Adele");
		assertTrue(songList.size() == 1);
		assertEquals(songList.get(0).getArtist(), "Adele");
	}
	
	@Test
	void testSearchSongByArtistNotInLibrary() {
		lm.addSong("Set Fire To The Rain");
		ArrayList<Song> songList = lm.searchSongByArtist("Laufey");
		assertTrue(songList.size() == 0);
	}
	
	
	@Test
	void testSearchAlbumByTitle() {
		lm.addAlbum("Sons");
		ArrayList<Album> albumList = lm.searchAlbumByTitle("Sons");
		assertTrue(albumList.size() == 1);
		assertEquals(albumList.get(0).getTitle(), "Sons");
	}
	
	@Test
	void testSearchAlbumByTitleNotInLibrary() {
		lm.addAlbum("Sons");
		ArrayList<Album> albumList = lm.searchAlbumByTitle("Goddess");
		assertTrue(albumList.size() == 0);
	}
	
	@Test
	void testSearchAlbumByArtist() {
		lm.addAlbum("Sons");
		ArrayList<Album> albumList = lm.searchAlbumByArtist("The Heavy");
		assertTrue(albumList.size() == 1);
		assertEquals(albumList.get(0).getArtist(), "The Heavy");
	}
	
	@Test
	void testSearchAlbumByArtistMultiple() {
		lm.addAlbum("19");
		lm.addAlbum("21");
		ArrayList<Album> albumList = lm.searchAlbumByArtist("Adele");
		assertTrue(albumList.size() == 2);
		assertEquals(albumList.get(0).getArtist(), "Adele");
		assertEquals(albumList.get(1).getArtist(), "Adele");
	}
	
	@Test
	void testSearchAlbumByArtistNotInLibrary() {
		lm.addAlbum("Sons");
		ArrayList<Album> albumList = lm.searchAlbumByArtist("Laufey");
		assertTrue(albumList.size() == 0);
	}
	
	@Test
	void testGetSongTitles() {
		lm.addSong("Set Fire to the Rain");
		lm.addSong("Winter Winds");
		lm.addSong("Mis Ojos");
		assertTrue(lm.getSongTitles().size() == 3);
		assertEquals(lm.getSongTitles().get(0), "Set Fire to the Rain");
		assertEquals(lm.getSongTitles().get(2), "Mis Ojos");
	}
	
	@Test
	void testGetSongTitlesEmpty() {
		lm.addSong("Goddess");
		lm.addSong("Letter to my 13 Year Old Self");
		assertTrue(lm.getSongTitles().size() == 0);
	}
	
	@Test
	void testGetArtists() {
		lm.addSong("Set Fire to the Rain");
		assertTrue(lm.getArtists().size() == 1);
		lm.addSong("Mis Ojos");
		assertTrue(lm.getArtists().size() == 2);
		lm.addSong("Rolling in the deep");
		assertTrue(lm.getArtists().size() == 2);
		assertEquals(lm.getArtists().get(0), "Adele");
		assertEquals(lm.getArtists().get(1), "Mana");
	}
	
	@Test
	void testGetArtistsEmpty() {
		lm.addSong("Goddess");
		lm.addSong("Letter to my 13 Year Old Self");
		assertTrue(lm.getArtists().size() == 0);
	}
	
	@Test
	void testGetAlbumTitles() {
		lm.addAlbum("19");
		lm.addAlbum("Waking up");
		assertTrue(lm.getAlbumTitles().size() == 2);
		assertEquals(lm.getAlbumTitles().get(0), "19");
		assertEquals(lm.getAlbumTitles().get(1), "Waking Up");
	}
	
	@Test
	void testGetAlbumTitlesEmpty() {
		lm.addAlbum("Bewitched");
		lm.addAlbum("Everything I Know About Love");
		assertTrue(lm.getAlbumTitles().size() == 0);
	}
	
	@Test
	void testGetPlayLists() {
		lm.createPlayList("Happy");
		lm.createPlayList("Sad");
		assertTrue(lm.getPlayLists().size() == 2);
		assertEquals(lm.getPlayLists().get(0), "Happy");
		assertEquals(lm.getPlayLists().get(1), "Sad");
	}
	
	@Test
	void testGetPlayListsEmpty() {
		assertTrue(lm.getPlayLists().size() == 0);
	}
	
	@Test
	void testFavoriteSongNotInLibrary() {
		lm.addSong("rolling in the deep");
		assertFalse(lm.favoriteSong("Mis Ojos"));
	}
	
	@Test
	void testFavoriteSong() {
		lm.addSong("Mis Ojos");
		assertTrue(lm.favoriteSong("Mis Ojos"));
	}
	
	@Test
	void testFavoriteSongMultipleYes() {
		initLM2();
		String input = ("yes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertTrue(lm2.favoriteSong("Lullaby"));
	}
	
	@Test
	void testFavoriteSongMultipleNo() {
		initLM2();
		String input = ("no\nOneRepublic\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertTrue(lm2.favoriteSong("Lullaby"));
	}
	
	@Test
	void testFavoriteSongMultipleNoInvalidArtist() {
		initLM2();
		String input = ("no\nLaufey\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertFalse(lm2.favoriteSong("Lullaby"));
	}
	
	@Test
	void testFavoriteSongMultipleNotYesOrNo() {
		initLM2();
		String input = ("maybe\nyes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertTrue(lm2.favoriteSong("Lullaby"));
	}
	
	@Test
	void testGetFavoriteSongs() {
		lm.addSong("rolling in the deep");
		lm.addSong("mis ojos");
		lm.favoriteSong("rolling in the deep");
		assertTrue(lm.getFavoriteSongs().size() == 1);
		assertEquals(lm.getFavoriteSongs().get(0), "Rolling in the Deep");
		lm.favoriteSong("mis ojos");
		assertTrue(lm.getFavoriteSongs().size() == 2);
		assertEquals(lm.getFavoriteSongs().get(1), "Mis Ojos");
	}
	
	@Test
	void testGetFavoriteSongsEmpty() {
		lm.addSong("rolling in the deep");
		lm.addSong("mis ojos");
		assertTrue(lm.getFavoriteSongs().size() == 0);
		lm.favoriteSong("Letter to my 13 year old self");
		assertTrue(lm.getFavoriteSongs().size() == 0);
	}
	
	@Test
	void testRateSongNotInLibrary() {
		lm.addSong("mis ojos");
		assertFalse(lm.rateSong("rolling in the deep", 4));
	}
	
	@Test
	void testRateSong() {
		lm.addSong("Mis ojos");
		assertTrue(lm.rateSong("mis Ojos", 3));
	}
	
	@Test
	void testRateSongMultipleYes() {
		initLM2();
		String input = ("yes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertTrue(lm2.rateSong("Lullaby", 4));
	}
	
	@Test
	void testRateSongMultipleNo() {
		initLM2();
		String input = ("no\nOneRepublic\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertTrue(lm2.rateSong("Lullaby", 4));
	}
	
	@Test
	void testRateSongMultipleNoInvalidArtist() {
        initLM2();
		String input = ("no\nLaufey\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertFalse(lm2.rateSong("Lullaby", 4));
	}
	
	@Test
	void testRateSongMultipleNotYesOrNo() {
        initLM2();
		String input = ("maybe\nyes\n");
		ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        assertTrue(lm2.rateSong("Lullaby", 4));
	}
	
	@Test
	void testMSSearchSongByTitle() {
		assertTrue(lm.mS_SearchSongByTitle("rolling in the deep").size() == 1);
	}
	
	@Test
	void testMSSearchSongByArtist() {
		assertTrue(lm.mS_SearchSongByArtist("Mana").size() == 12);
	}
	
	@Test
	void testMSSearchAlbumByTitle() {
		assertTrue(lm.mS_SearchAlbumByTitle("19").size() == 1);
	}
	
	@Test
	void testMSSearchAlbumByArtist() {
		assertTrue(lm.mS_SearchAlbumByArtist("Adele").size() == 2);
	}
}
