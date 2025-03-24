package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class LibraryModel {
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playLists;
	//private ArrayList<Song> recentSongs;
	
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.playLists = new ArrayList<PlayList>();
		this.songs = new ArrayList<Song>();
		//this.recentSongs = new ArrayList<Song>();
	}
	
	public void createPlayList(String title) {
		boolean flag = false;
		for (PlayList pl: playLists) {
			if (pl.getTitle().equalsIgnoreCase(title)){
				flag = true;
			}
		}
		if(!flag) {
			PlayList playlist = new PlayList(title);
			playLists.add(playlist);
		}
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
		if(!(p.getPlayList().contains(s))) {
			p.addSong(s);
		}
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
		PlayList recentSongs = searchPlayList("Recently Played Songs");
		recentSongs.removeSong(s); // removes song if already in list to maintain order
		recentSongs.addToFront(s); 
		recentSongs.length10(); // will be called each time song is played, so ensures list will never be > 10
	}
	
	public void updateTop10MostPlayedSongs() {
	    ArrayList<Song> topSongs = new ArrayList<Song>(songs);
	    topSongs.sort((s1, s2) -> Integer.compare(s2.getPlays(), s1.getPlays())); // sort in descending order
	    
	    // checks if playlist with same name already exists
	    if (!(playLists.contains(new PlayList("Most Played Songs")))) {
	    	createPlayList("Most Played Songs");
	    	for (Song s: topSongs) {
	    		PlayList pl = searchPlayList("Most Played Songs");
	    		addSongToPlayList(pl, s);
	    		pl.length10(); // to ensure list length <= 10
	    	}
	    }
	    else {
	    	// since PlayList equals method overwritten to check titles, this should work as intended
	    	// remove old Most Played Songs Playlist, create the new one
	    	playLists.remove(new PlayList("Most Played Songs")); // this works since .remove uses .equals, which was overridden to check title
	    	
	    	createPlayList("Most Played Songs");
	    	for (Song s: topSongs) {
	    		PlayList pl = searchPlayList("Most Played Songs");
	    		addSongToPlayList(pl, s);
	    		pl.length10();
	    	}
	    }
	}
	
	public ArrayList<Song> searchSongByGenre(String genre) {
		ArrayList<Song> songList = new ArrayList<Song>();
		for (Song s: songs) {
			if (s.getGenre().equalsIgnoreCase(genre)) {
				songList.add(s);
			}
		}
		
		return songList;
	}
	
	public ArrayList<Song> getSongsSortedByTitle() {
	    ArrayList<Song> sortedSongs = new ArrayList<Song>(songs);
	    sortedSongs.sort((s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle()));
	    return sortedSongs;
	}
	
	public ArrayList<Song> getSongsSortedByArtist() {
	    ArrayList<Song> sortedSongs = new ArrayList<Song>(songs);
	    sortedSongs.sort((s1, s2) -> s1.getArtist().compareToIgnoreCase(s2.getArtist()));
	    return sortedSongs;
	}
	
	public ArrayList<Song> getSongsSortedByRating() {
	    ArrayList<Song> sortedSongs = new ArrayList<Song>();
	    for (Song s : songs) {
	        if (s.getRating() > 0) {
	            sortedSongs.add(s);
	        }
	    }
	    sortedSongs.sort((s1, s2) -> Integer.compare(s1.getRating(), s2.getRating()));
	    return sortedSongs;
	}
	
	public ArrayList<Song> getFavorites(){
		ArrayList<Song> favs = new ArrayList<Song>();
		for (Song s : songs) {
			if(s.getFavorite()) {
				favs.add(s);
			}
		}
		return favs;
	}
	
	public ArrayList<String> getGenres(){
		ArrayList<String> genres = new ArrayList<String>();
		for (Song s : songs) {
			if (!genres.contains(s.getGenre())) {
				genres.add(s.getGenre());
			}
		}
		return genres;
	}
	
	public ArrayList<Song> getTopRated(){
		ArrayList<Song> topRated = new ArrayList<Song>();
		for (Song s : songs) {
			if (s.getRating() >= 4) {
				topRated.add(s);
			}
		}
		return topRated;
	}
	
	public void removeSong(Song s) {
		songs.remove(s);
		// need to remove from album as well
		String albumTitle = s.getAlbum();
		
		boolean flag = false;
		ArrayList<Album> toRemove = new ArrayList<Album>();
		for (Album alb: albums) {
			if (alb.getTitle().equalsIgnoreCase(albumTitle)) {
				alb.removeSong(s);
				// check if album is empty, remove if it is
				if (alb.getAlbum().size() == 0) {
					flag = true;
					toRemove.add(alb);
				}
			}
		}
		
		for (PlayList pl: playLists) {
			if (pl.getPlayList().contains(s)) {
				pl.removeSong(s);
			}
		}
		
		if (flag) {
			albums.remove(toRemove.getFirst());
		}
	}
	
	public void removeAlbum(Album alb) {
		for(Song s: alb.getAlbum()) {
			removeSong(s);
		}
		albums.remove(alb);
	}
	
	public void shuffleSongs() {
		Collections.shuffle(songs);
	}
	
	public void shufflePlayList(String title) {
		for (PlayList pl: playLists) {
			if (pl.getTitle().equals(title)) {
				pl.shuffleSongs();
			}
		}
	}
	
	public void getSongAlbumInfo(Song s) {
		for (Album alb: albums) {
			if (alb.getAlbum().contains(s)) {
				alb.printAlbumDetails();
			}
		}
	}
			
}
