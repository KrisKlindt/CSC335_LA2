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
		// check to see if there is already a PlayList with the same name
		boolean flag = true;
		for(PlayList p: playLists) {
			if(p.getTitle().equalsIgnoreCase(title)) {
				flag = false;
				System.out.println("There already exists a PlayList with this name");
			}
		}
		
		if (flag) {
			PlayList playlist = new PlayList(title);
			playLists.add(playlist);
			System.out.println("PlayList added successfully");
		}
	}
	
	public void addSong(String title) {
		ArrayList<Song> song = mStore.searchSongByTitle(title, false);
		if (song.size() == 0) {
			System.out.println("This song title is not in the music store, cannot add to library");
		}
		
		else {
			if (song.size() == 1) {
				songs.addAll(song);
				System.out.println("Song added to the library");
			}
			
			else {
				System.out.println("There are multiple songs with this name");
				for (Song s: song) {
					s.printAllDetails();
				}
				
				System.out.println("Would you like to add all songs? (yes or no)");
				Scanner scanner = new Scanner(System.in);
		    	
		    	int count = 0;
		    	while(count < 1) {
			    	String choice = scanner.nextLine();
			    	
			    	if(choice.equalsIgnoreCase("yes")) {
			    		songs.addAll(song);
			    		System.out.println("All songs added to the library");
			    		count++;
			    	}
			    	
			    	else if (choice.equalsIgnoreCase("no")) {
			    		System.out.println("Which artist's song would you like to add?");
			    		String artistName = scanner.nextLine();
			    		boolean flag = false;
			    		for (Song s: song) {
			    			if(s.getArtist().equalsIgnoreCase(artistName)){
			    				songs.add(s);
			    				flag = true;
			    				System.out.println("Song added to the library");
			    				break;
			    			}
			    		}
			    		
			    		if(flag == false) {
			    			System.out.println("None of the chosen songs were written by this artist");
			    		}
			    		count++;
			    	}
			    	
			    	else {
			    		System.out.println("You typed neither yes nor no, please type either yes or no");
			    	}
				}
			}
		}
	}
	

	// This is only meant to be used to add songs to the library when they 
	// are added to a PlayList or added via an album
	private void addSong(Song song) {
		if (!songs.contains(song)) {
			songs.add(song);
		}
	}
	
	public void addAlbum(String title) {
		ArrayList<Album> alb = mStore.searchAlbumByTitle(title, false);
		if (alb.size() == 0) {
			System.out.println("This album title is not in the music store, cannot add to library");
		}
		else {
			if (alb.size() == 1) {
				albums.addAll(alb);
				for (Song s: alb.get(0).getAlbum()) {
					addSong(s);
				}
				System.out.println("Album added to the library");
			}
			
			else {
				System.out.println("There are multiple albums with this name");
				for (Album a: alb) {
					a.printTitleAndArtist();
					System.out.println(); // space between each album
				}
				
				System.out.println("Would you like to add all albums? (yes or no)");
				Scanner scanner = new Scanner(System.in);
		    	
		    	int count = 0;
		    	while(count < 1) {
			    	String choice = scanner.nextLine();
			    	
			    	if(choice.equalsIgnoreCase("yes")) {
			    		albums.addAll(alb);
			    		for (Album a: alb) {
							for (Song s: a.getAlbum()) {
								addSong(s);
							}
						}
			    		System.out.println("All albums were added to the library");
			    		count++;
			    	}
			    	
			    	else if (choice.equalsIgnoreCase("no")){
			    		System.out.println("Which artist's album would you like to add?");
			    		String artistName = scanner.nextLine();
			    		boolean flag = false;
			    		
			    		for (Album a : alb) {
			    			if(a.getArtist().equalsIgnoreCase(artistName)){
			    				albums.add(a);
			    				flag = true;
			    				for (Song s: a.getAlbum()) {
			    					addSong(s);
			    				}
			    				System.out.println("Album added to the library");
			    				break;
			    			}
			    		}
			    		
			    		if(flag == false) {
			    			System.out.println("None of the chosen albums were written by this artist");
			    		}
			    		count++;
			    	}
			    	
			    	else {
			    		System.out.println("You typed neither yes nor no, please type either yes or no");
			    	}
		    	}
			}
		}
	}
	
	public boolean searchPlayList(String title) {
		boolean flag = false;
		for (PlayList p : playLists) {
			if (p.getTitle().equalsIgnoreCase(title)) {
				p.printPlaylist();
				flag = true;
			}
		}
		
		if (flag == false) {
			System.out.println("There is no playlist in the library with this name");
		}
		return flag;
	}
	
	public boolean addSongToPlayList(String pLTitle, String songTitle) {
		Scanner scanner = new Scanner(System.in);
		
		boolean flag = false;
		for(PlayList p : playLists) {
			if (p.getTitle().equalsIgnoreCase(pLTitle)) {
				flag = true;
				
				ArrayList<Song> song = mStore.searchSongByTitle(songTitle, false);
				
				if (song.size() == 0) {
					System.out.println("This song title is not in the music store");
					return false;
				}
				
				else if (song.size() == 1) {
					p.addSong(song.get(0)); // adds to the PlayList
					addSong(song.get(0)); // adds to the library song list, if not already in there
					System.out.println("Song added to PlayList and library");
					return true;
				}
				
				else {
					System.out.println("There are multiple songs with this name");
					for (Song s: song) {
						s.printAllDetails();
					}
					
					System.out.println("Would you like to add all songs? (yes or no)");
			    	
			    	int count = 0;
			    	while(count < 1) {
				    	String choice = scanner.nextLine();
				    	
				    	if(choice.equalsIgnoreCase("yes")) {
				    		for (Song s: song) {
								p.addSong(s); // adds to the PlayList
								addSong(s); // adds to the library, if not already there
							}
				    		System.out.println("All songs added to the PlayList and library");
				    		return true;
				    	}
				    	
				    	else if (choice.equalsIgnoreCase("no")) {
				    		System.out.println("Which artist's song would you like to add?");
				    		String artistName = scanner.nextLine();
				    		boolean f = false;
				    		for (Song s: song) {
				    			if(s.getArtist().equalsIgnoreCase(artistName)){
				    				p.addSong(s); // adds to the PlayList
				    				addSong(s); // adds to the library, if not already there
				    				System.out.println("Song added to the Playlist and library");
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
			}
		}
		
		if (!(flag)) {
			System.out.println("There is no PlayList with that name in the library");
			System.out.println("Please create a PlayList with that name first, and then add a song to it");
		}
		return false;
	}
	
	public boolean removeSongFromPlayList(String pLTitle, String songTitle) {
		Scanner scanner = new Scanner(System.in);
		
		boolean flag = false;
		for(PlayList p : playLists) {
			if (p.getTitle().equalsIgnoreCase(pLTitle)) {
				flag = true;
				
				ArrayList<Song> pl = p.getPlayList();
				
				if (pl.size() == 0) {
					System.out.println("This PlayList is empty");
					return false;
				}
				
				else {
					ArrayList<Song> song = new ArrayList<Song>();
					for (Song s: pl) {
						if(s.getTitle().equalsIgnoreCase(songTitle)) {
							song.add(s);
						}
					}
					
					if (song.size() == 1) {
						p.removeSong(song.get(0));
						return true;
					}
					
					else {
						System.out.println("There are multiple songs with this name in the PlayList");
						for (Song s: song) {
							s.printAllDetails();
						}
						
						System.out.println("Would you like to remove all songs? (yes or no)");
				    	
				    	int count = 0;
				    	while(count < 1) {
					    	String choice = scanner.nextLine();
					    	
					    	if(choice.equalsIgnoreCase("yes")) {
					    		for (Song s: song) {
									p.removeSong(s); // removes from the PlayList
								}
					    		System.out.println("All songs removed from the PlayList");
					    		count++;
					    		return true;
					    	}
					    	
					    	else if (choice.equalsIgnoreCase("no")) {
					    		System.out.println("Which artist's song would you like to remove?");
					    		String artistName = scanner.nextLine();
					    		boolean f = false;
					    		for (Song s: song) {
					    			if(s.getArtist().equalsIgnoreCase(artistName)){
					    				p.removeSong(s); // removes from the PlayList
					    				System.out.println("Song removed from the Playlist");
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
				}
			}
		}
		
		if (!(flag)) {
			System.out.println("There is no PlayList with that name in the library");
		}
		return false;
	}
	
 	public ArrayList<Song> searchSongByTitle(String title) {
		ArrayList<Song> songList = new ArrayList<Song>();
		for (Song s: songs) {
			if (s.getTitle().equalsIgnoreCase(title)) {
				songList.add(s);
			}
		}
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the library");
		}
		else {
			for (Song song: songList) {
				song.printAllDetails();
				System.out.println(); // so there is a space between each song
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
