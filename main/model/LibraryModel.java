package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class LibraryModel {
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playLists;
	private LinkedList<Song> recentSongs;
	
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.playLists = new ArrayList<PlayList>();
		this.songs = new ArrayList<Song>();
		this.recentSongs = new LinkedList<Song>();
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
	
	public ArrayList<Song> getSongs(){
		ArrayList<Song> sngs = new ArrayList<Song>();
		
		for(Song s: songs) {
			sngs.add(s);
		}
		
		return sngs;
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
	
	public ArrayList<Album> getAlbums(){
		ArrayList<Album> albs = new ArrayList<Album>();
		for (Album a: albums) {
			albs.add(a);
		}
		
		return albs;
	}
	
	public ArrayList<String> getPlayLists(){
		ArrayList<String> pls = new ArrayList<String>();
		
		for (PlayList p: playLists) {
			String title = p.getTitle();
			pls.add(title);
		}
		
		return pls;
	}
	
	public ArrayList<PlayList> getPLs(){
		ArrayList<PlayList> pLs = new ArrayList<PlayList>();
		
		for (PlayList p: playLists) {
			pLs.add(p);
		}
		
		return pLs;
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
	
	public void playSong(Song s) {
		s.play();
		updateRecentSongs(s);
	}
	
	private void updateRecentSongs(Song s) {
		recentSongs.remove(s); // removes song if already in list to maintain order
		recentSongs.addFirst(s); 
		if (recentSongs.size() > 10) { // ensure only 10 songs are kept
            recentSongs.removeLast();
        }
	}
	
	public LinkedList<Song> getRecentSongs() {
        return new LinkedList<>(recentSongs);
    }
}
