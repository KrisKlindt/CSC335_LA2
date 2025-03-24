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
	MusicStore ms = new MusicStore();
	Song song = ms.searchSongByTitle("Politik", false).getFirst();
	Song song2 = ms.searchSongByTitle("He Won't Go", false).getFirst();
	Song song3 = ms.searchSongByTitle("Mis Ojos", false).getFirst();
	Album alb = ms.searchAlbumByTitle("A Rush of Blood to the Head", false).getFirst();
	Album alb2 = ms.searchAlbumByTitle("21", false).getFirst();
	Album alb3 = ms.searchAlbumByTitle("Cuando Los Angeles Lloran", false).getFirst();
	
	void initPlayLists() {
		lm.createPlayList("sad");
		lm.createPlayList("happy");
	}
	
	void addSongs() {
		lm.addSong(song);
		lm.addSong(song2);
		lm.addSong(song3);
	}
	
	void addAlbums() {
		lm.addAlbum(alb);
		lm.addAlbum(alb2);
		lm.addAlbum(alb3);
	}
	
	@Test
	void createAndSearchPlayListTest() {
		initPlayLists();
		PlayList pl = lm.searchPlayList("sad");
		assertEquals(pl.getTitle(), "sad");
	}
	
	@Test
	void addAndGetSongSTest() {
		lm.addSong(song);
		assertEquals(lm.getSongs().size(), 1);
		assertEquals(lm.getSongs().getFirst().getTitle(), "Politik");
	}
	
	@Test
	void addAndGetAlbumsTest() {
		Album alb = ms.searchAlbumByTitle("21", false).getFirst();
		lm.addAlbum(alb);
		assertEquals(lm.getAlbums().size(), 1);
		assertEquals(lm.getAlbums().getFirst().getTitle(), "21");
	}
	
	@Test
	void addSongToPlayListTest() {
		initPlayLists();
		PlayList pl = lm.searchPlayList("sad");
		lm.addSongToPlayList(pl, song);
		assertEquals(pl.getPlayList().getFirst().getTitle(), "Politik");
	}
	
	@Test
	void removeSongFromPlayListTest() {
		initPlayLists();
		PlayList pl = lm.searchPlayList("sad");
		lm.addSongToPlayList(pl, song);
		assertEquals(pl.getPlayList().getFirst().getTitle(), "Politik");
		lm.removeSongFromPlayList(pl, song);
		assertEquals(pl.getPlayList().size(), 0);
	}
	
	@Test
	void searchSongByTitleTest() {
		addSongs();
		assertEquals(lm.getSongs().size(), 3);
		Song song = lm.searchSongByTitle("He Won't Go").getFirst();
		assertEquals(song.getTitle(), "He Won't Go");
	}
	
	@Test
	void searchSongByArtistTest() {
		addSongs();
		Song song = lm.searchSongByArtist("Mana").getFirst();
		assertEquals(song.getArtist(), "Mana");
		assertEquals(song.getTitle(), "Mis Ojos");
	}
	
	@Test
	void searchAlbumByTitleTest() {
		addAlbums();
		Album alb = lm.searchAlbumByTitle("21").getFirst();
		assertEquals(alb.getTitle(), "21");
		assertEquals(alb.getArtist(), "Adele");
	}
	
	@Test
	void searchAlbumByArtistTest() {
		addAlbums();
		Album alb = lm.searchAlbumByArtist("Mana").getFirst();
		assertEquals(alb.getTitle(), "Cuando Los Angeles Lloran");
		assertEquals(alb.getArtist(), "Mana");
	}
	
	@Test
	void getSongTitlesTest() {
		addSongs();
		ArrayList<String> songs = lm.getSongTitles();
		assertEquals(songs.size(), 3);
		assertEquals(songs.getFirst(), "Politik");
		assertEquals(songs.getLast(), "Mis Ojos");
	}
	
	@Test
	void getSongsTest() {
		addSongs();
		assertEquals(lm.getSongs().getFirst(), song);
		assertEquals(lm.getSongs().getLast(), song3);
	}
	
	@Test
	void getArtistsTest(){
		addSongs();
		assertEquals(lm.getArtists().getFirst(), "Coldplay");
		assertEquals(lm.getArtists().getLast(), "Mana");
	}
	
	@Test
	void getAlbumTitlesTest() {
		addAlbums();
		assertEquals(lm.getAlbumTitles().get(1), "21");
		assertEquals(lm.getAlbumTitles().getLast(), "Cuando Los Angeles Lloran");
	}
	
	@Test
	void getAlbumsTest() {
		addAlbums();
		assertEquals(lm.getAlbums().getFirst(), alb);
		assertEquals(lm.getAlbums().getLast(), alb3);
	}
	
	@Test
	void getPlayListsTest() {
		initPlayLists();
		assertEquals(lm.getPlayLists().getFirst(), "sad");
		assertEquals(lm.getPlayLists().getLast(), "happy");
	}
	
	@Test
	void getPLsTest() {
		initPlayLists();
		assertEquals(lm.getPLs().getFirst().getTitle(), "sad");
		assertEquals(lm.getPLs().getLast().getTitle(), "happy");
	}
	
	@Test
	void markAndGetFavoriteSongsTest() {
		addSongs();
		lm.favoriteSong(song);
		lm.favoriteSong(song3);
		assertEquals(lm.getFavoriteSongs().getFirst(), "Politik");
		assertEquals(lm.getFavoriteSongs().getLast(), "Mis Ojos");
	}
	
	@Test
	void rateAndGetTopRatedTest() {
		addSongs();
		lm.rateSong(song, 4);
		lm.rateSong(song2, 3);
		lm.rateSong(song3, 5);
		assertEquals(song.getRating(), 4);
		assertEquals(lm.getTopRated().getFirst().getTitle(), "Politik");
		assertEquals(lm.getTopRated().getLast().getTitle(), "Mis Ojos");
	}
	
	@Test
	void playAndGetRecentSongsTest() {
		addSongs();
		lm.playSong(song);
		lm.playSong(song3);
		lm.playSong(song2);
		lm.playSong(song3);
		assertEquals(song3.getPlays(), 2);
		assertEquals(lm.searchPlayList("Recently Played Songs").getPlayList().getFirst(), song3);
		assertEquals(lm.searchPlayList("Recently Played Songs").getPlayList().getLast(), song);
	}
	
	@Test
	void getMostPlayedTest() {
		addSongs();
		lm.playSong(song);
		lm.playSong(song2);
		lm.playSong(song2);
		lm.playSong(song3);
		lm.playSong(song3);
		lm.playSong(song3);
		assertEquals(lm.searchPlayList("Most Played Songs").getPlayList().getFirst(), song3);
		assertEquals(lm.searchPlayList("Most Played Songs").getPlayList().getLast(), song);
	}
	
	@Test
	void searchSongByGenreTest() {
		addSongs();
		assertEquals(lm.searchSongByGenre("Latin").getFirst().getTitle(), "Mis Ojos");
		assertEquals(lm.searchSongByGenre("Pop").getFirst().getTitle(), "He Won't Go");
	}
	
	@Test
	void getSongsSortedByTitleTest() {
		addSongs();
		ArrayList<Song> sorted = lm.getSongsSortedByTitle();
		assertEquals(sorted.getFirst(), song2);
		assertEquals(sorted.getLast(), song);
	}
	
	@Test
	void getSongsSortedByArtistTest() {
		addSongs();
		ArrayList<Song> sorted = lm.getSongsSortedByArtist();
		assertEquals(sorted.getFirst(), song2);
		assertEquals(sorted.getLast(), song3);
	}
	
	@Test
	void getSongsSortedByRatingTest() {
		addSongs();
		song.rateSong(5);
		song2.rateSong(1);
		song3.rateSong(3);
		ArrayList<Song> sorted = lm.getSongsSortedByRating();
		assertEquals(sorted.getFirst(), song2);
		assertEquals(sorted.getLast(), song);
	}
	
	@Test
	void getFavoritesTest() {
		addSongs();
		song.markAsFavorite();
		song3.markAsFavorite();
		ArrayList<Song> sorted = lm.getFavorites();
		assertEquals(sorted.getFirst(), song);
		assertEquals(sorted.getLast(), song3);
	}
	
	@Test
	void getGenresTest() {
		addSongs();
		ArrayList<String> genres = lm.getGenres();
		assertEquals(genres.getFirst(), "Alternative");
		assertEquals(genres.get(1), "Pop");
		assertEquals(genres.getLast(), "Latin");
	}
	
	@Test
	void removeSongTest() {
		lm.addSong(song);
		lm.removeSong(song);
		assertEquals(lm.getSongs().size(), 0);
	}
	
	@Test
	void removeAlbumTest() {
		lm.addAlbum(alb);
		lm.removeAlbum(alb);
		assertEquals(lm.getAlbums().size(), 0);
	}
	
	@Test
	void shuffleSongsTest() {
		addSongs();
		lm.shuffleSongs();
	}
	
	@Test
	void shufflePlayListTest() {
		lm.createPlayList("playlist");
		PlayList pl = lm.getPLs().getFirst();
		pl.addSong(song);
		pl.addSong(song2);
		pl.addSong(song3);
		lm.shufflePlayList("playlist");
	}
	
	@Test
	void albumDetailsTest() {
		lm.addSong(song);
		lm.getSongAlbumInfo(song);
	}
}
