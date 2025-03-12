package model;

import java.util.ArrayList;
import java.util.Scanner;

import database.MusicStore;

public class LibraryModel {
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playLists;
	private MusicStore mStore;
	
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.playLists = new ArrayList<PlayList>();
		this.songs = new ArrayList<Song>();
		this.mStore = new MusicStore();
	}
	
	public void createPlayList(String title) {
			PlayList playlist = new PlayList(title);
			playLists.add(playlist);
	}
	
	public void addSong(Song song) {
		if (!songs.contains(song)) {
			songs.add(song);
		}
	}
	
	public void addAlbum(Album alb) {
		if (!albums.contains(alb)) {
			albums.add(alb);
		}
	}
	
	public PlayList searchPlayList(String title) {
		for (PlayList p : playLists) {
			if (p.getTitle().equalsIgnoreCase(title)) {
				return p;
			}
		}
		return null;
	}
	
	public void addSongToPlayList(PlayList p, Song s) {
		p.addSong(s);
	}
	
	public void removeSongFromPlayList(PlayList p, Song s) {
		p.removeSong(s);
	}
	
 	public ArrayList<Song> searchSongByTitle(String title) {
		ArrayList<Song> songList = new ArrayList<Song>();
		for (Song s: songs) {
			if (s.getTitle().equalsIgnoreCase(title)) {
				songList.add(s);
			}
		}
		
		return songList;
	}
	
	public ArrayList<Song> searchSongByArtist(String artist) {
		ArrayList<Song> songList = new ArrayList<Song>();
		for (Song s: songs) {
			if (s.getArtist().equalsIgnoreCase(artist)) {
				songList.add(s);
			}
		}
		
		return songList;
	}
	
	public ArrayList<Album> searchAlbumByTitle(String title) {
		ArrayList<Album> albumList = new ArrayList<Album>();
		for (Album album : albums) {
			if (album.getTitle().equalsIgnoreCase(title)) {
				albumList.add(album);
			}
		}
		
		return albumList;
	}
	
	public ArrayList<Album> searchAlbumByArtist(String artist) {
		ArrayList<Album> albumList = new ArrayList<Album>();
		for (Album album : albums) {
			if (album.getArtist().equalsIgnoreCase(artist)) {
				albumList.add(album);
			}
		}
		
		return albumList;
	}

	public ArrayList<String> getSongTitles(){
		ArrayList<String> titles = new ArrayList<String>();
		
		for (Song s: songs) {
			String title = s.getTitle();
			titles.add(title);
		}
		
		return titles;
	}
	
	public ArrayList<String> getArtists(){
		ArrayList<String> artists = new ArrayList<String>();
		
		for (Song s: songs) {
			String artist = s.getArtist();
			if(!(artists.contains(artist))) {
				artists.add(artist);
			}
		}
		
		return artists;
	}
	
	public ArrayList<String> getAlbumTitles(){
		ArrayList<String> albumTitles = new ArrayList<String>();
		
		for (Album a: albums) {
			String title = a.getTitle();
			albumTitles.add(title);
		}
		
		return albumTitles;
	}
	
	public ArrayList<String> getPlayLists(){
		ArrayList<String> pls = new ArrayList<String>();
		
		for (PlayList p: playLists) {
			String title = p.getTitle();
			pls.add(title);
		}
		
		return pls;
	}
	
	public ArrayList<String> getFavoriteSongs(){
		ArrayList<String> titles = new ArrayList<String>();
		
		for (Song s: songs) {
			if(s.getFavorite()) {
				String title = s.getTitle();
				titles.add(title);
			}
		}
		
		return titles;
	}

	public void favoriteSong(Song s) {
		s.markAsFavorite();
	}
	
	public void rateSong(Song s, int rating) {
		s.rateSong(rating);
	}
	
	// These 4 methods are so that there is not a need to make a MusicStore object
	// in both LibraryModel and View
	
	public ArrayList<Song> mS_SearchSongByTitle(String title){
		ArrayList<Song> s = mStore.searchSongByTitle(title, true);
		return s;
	}
	
	public ArrayList<Song> mS_SearchSongByArtist(String artist){
		ArrayList<Song> s = mStore.searchSongByArtist(artist, true);
		return s;
	}
	
	public ArrayList<Album> mS_SearchAlbumByTitle(String title){
		ArrayList<Album> albs = mStore.searchAlbumByTitle(title, true);
		return albs;
	}
	
	public ArrayList<Album> mS_SearchAlbumByArtist(String artist){
		ArrayList<Album> albs = mStore.searchAlbumByArtist(artist, true);
		return albs;
	}
}
