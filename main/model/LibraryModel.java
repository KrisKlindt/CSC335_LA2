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
		
		if (songList.size() == 0) {
			System.out.println("This song artist is not in the library");
		}
		else {
			for (Song song: songList) {
				song.printAllDetails();
				System.out.println(); // so there is a space between each song
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
		
		if (albumList.size() == 0) {
			System.out.println("This album title is not in the library");
		}
		else {
			for (Album alb: albumList) {
				alb.printAlbumDetails();
				System.out.println(); // so there is a space between each album
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
		
		if (albumList.size() == 0) {
			System.out.println("This album artist is not in the library");
		}
		else {
			for (Album alb : albums) {
				alb.printAlbumDetails();
				System.out.println(); // so there is a space between each album
			}
		}
		
		return albumList;
	}

	public ArrayList<String> getSongTitles(){
		ArrayList<String> titles = new ArrayList<String>();
		
		for (Song s: songs) {
			String title = s.getTitle();
			titles.add(title);
			System.out.println(title);
			System.out.println(); // for a space between each song
		}
		
		return titles;
	}
	
	public ArrayList<String> getArtists(){
		ArrayList<String> artists = new ArrayList<String>();
		
		for (Song s: songs) {
			String artist = s.getArtist();
			if(!(artists.contains(artist))) {
				artists.add(artist);
				System.out.println(artist);
				System.out.println(); // for a space between artists
			}
		}
		
		return artists;
	}
	
	public ArrayList<String> getAlbumTitles(){
		ArrayList<String> albumTitles = new ArrayList<String>();
		
		for (Album a: albums) {
			String title = a.getTitle();
			albumTitles.add(title);
			System.out.println(title);
			System.out.println(); // for a space between each album
		}
		
		return albumTitles;
	}
	
	public ArrayList<String> getPlayLists(){
		ArrayList<String> pls = new ArrayList<String>();
		
		for (PlayList p: playLists) {
			String title = p.getTitle();
			pls.add(title);
			System.out.println(title);
			System.out.println(); // for a space between each play list
		}
		
		return pls;
	}
	
	public ArrayList<String> getFavoriteSongs(){
		ArrayList<String> titles = new ArrayList<String>();
		
		for (Song s: songs) {
			if(s.getFavorite()) {
				String title = s.getTitle();
				titles.add(title);
				System.out.println(title);
				System.out.println(); // for a space between each song
			}
		}
		
		return titles;
	}

	public boolean favoriteSong(String title) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Song> songList = new ArrayList<Song>();
		
		for (Song s: songs) {
			if (s.getTitle().equalsIgnoreCase(title)) {
				songList.add(s);
			}
		}
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the library");
			return false;
		}
		
		else if (songList.size() == 1){
			songList.get(0).markAsFavorite();
			return true;
		}
		
		else {
			System.out.println("There are multiple songs with this name in the library");
			for (Song s: songList) {
				s.printAllDetails();
			}
			
			System.out.println("Would you like to favorite all songs? (yes or no)");
	    	
	    	int count = 0;
	    	while(count < 1) {
		    	String choice = scanner.nextLine();
		    	
		    	if(choice.equalsIgnoreCase("yes")) {
		    		for (Song s: songList) {
						s.markAsFavorite();
					}
		    		System.out.println("All songs favorited");
		    		count++;
		    		return true;
		    	}
		    	
		    	else if (choice.equalsIgnoreCase("no")) {
		    		System.out.println("Which artist's song would you like to favorite?");
		    		String artistName = scanner.nextLine();
		    		boolean f = false;
		    		for (Song s: songList) {
		    			if(s.getArtist().equalsIgnoreCase(artistName)){
		    				s.markAsFavorite();
		    				System.out.println("Song favorited");
		    				f = true;
		    				return true;
		    			}
		    		}
		    		
		    		if(f == false) {
		    			System.out.println("None of the chosen songs were written by this artist");
		    		}
		    		count++;
		    		return false;
		    	}
		    	
		    	else {
		    		System.out.println("You typed neither yes nor no, please type either yes or no");
		    	}
	    	}
		}
		return false;
	}
	
	public boolean rateSong(String title, int rating) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Song> songList = new ArrayList<Song>();
		
		for (Song s: songs) {
			if (s.getTitle().equalsIgnoreCase(title)) {
				songList.add(s);
			}
		}
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the library");
			return false;
		}
		
		else if (songList.size() == 1){
			songList.get(0).rateSong(rating);
			return true;
		}
		
		else {
			System.out.println("There are multiple songs with this name in the library");
			for (Song s: songList) {
				s.printAllDetails();
			}
			
			System.out.println("Would you like to rate all songs? (yes or no)");
	    	
	    	int count = 0;
	    	while(count < 1) {
		    	String choice = scanner.nextLine();
		    	
		    	if(choice.equalsIgnoreCase("yes")) {
		    		for (Song s: songList) {
		    			System.out.println("Song to be rated: ");
		    			s.printAllDetails();
						s.rateSong(rating);
					}
		    		System.out.println("All songs rated");
		    		count++;
		    		return true;
		    	}
		    	
		    	else if (choice.equalsIgnoreCase("no")) {
		    		System.out.println("Which artist's song would you like to rate?");
		    		String artistName = scanner.nextLine();
		    		boolean f = false;
		    		for (Song s: songList) {
		    			if(s.getArtist().equalsIgnoreCase(artistName)){
		    				s.rateSong(rating);
		    				System.out.println("Song rated");
		    				f = true;
		    				return true;
		    			}
		    		}
		    		
		    		if(f == false) {
		    			System.out.println("None of the chosen songs were written by this artist");
		    		}
		    		count++;
		    		return false;
		    	}
		    	
		    	else {
		    		System.out.println("You typed neither yes nor no, please type either yes or no");
		    	}
	    	}
		}
		return false;
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
