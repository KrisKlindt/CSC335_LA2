package view;

import model.*;
import database.*;
import java.util.Scanner;
import java.util.ArrayList;

public class View {
	
	private LibraryModel library;
	private MusicStore mStore;
	
	public View() {
		this.library = new LibraryModel();
	}
	
	public static void main(String[] args) {
		View view = new View();
		view.run();
	}
	
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome User!\nThis is your music library where you can add songs and Albums that are available within our store!");
		int command = 0;
		while (command < 18) {
			System.out.println("Here are the features available to you.");
			System.out.println("1. Create a playlist");
			System.out.println("2. Add a song");
			System.out.println("3. Add an album");
			System.out.println("4. Search for a playlist");
			System.out.println("5. Add a song to a playlist");
			System.out.println("6. Remove a song from a playlist");
			System.out.println("7. Search for a song by the title");
			System.out.println("8. Search for a song by the artist");
			System.out.println("9. Search an an album by the title");
			System.out.println("10. Search for an album by the artist");
			System.out.println("11. Mark a song as a favorite");
			System.out.println("12. Rate a song 1 - 5");
			System.out.println("13. Get the titles of the songs in your library");
			System.out.println("14. Get the artists in your library");
			System.out.println("15. Get the titles of the albums in your library");
			System.out.println("16. Get the playlists in your library");
			System.out.println("17. Get your favorite songs in your library");
			
			System.out.println("Please enter the integer of the command you'd like to use: ");
			System.out.println("Or enter a negative integer to exit");
			
			command = getValidIntegerInput(scanner);
			scanner.nextLine();
			
			if (command < 0) {
				break;
			}
			if (command == 1) {
				System.out.println("What would you like to call this playlist?");
				String pName = scanner.nextLine();
				createPlayList(pName);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 2) {
				System.out.println("What song would you like to add?");
				String sTitle = scanner.nextLine();
				addSong(sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 3) {
				System.out.println("What album would you like to add?");
				String albName = scanner.nextLine();
				addAlbum(albName);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 4) {
				System.out.println("What playlist would you like to search for?");
				String pName = scanner.nextLine();
				searchPlayList(pName);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 5) {
				System.out.println("Please put in the playlist you'd like to add to: ");
				String pName = scanner.nextLine();
				System.out.println("Please put in the name of the song you'd like to add: ");
				String sTitle = scanner.nextLine();
				addSongToPlayList(pName, sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 6) {
				System.out.println("Please put in the playlist you'd like to remove from: ");
				String pName = scanner.nextLine();
				System.out.println("Please put in the name of the song you'd like to remove: ");
				String sTitle = scanner.nextLine();
				removeSongFromPlayList(pName, sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 7) {
				System.out.println("What song would you like to search for? ");
				String sTitle = scanner.nextLine();
				searchSongByTitle(sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 8) {
				System.out.println("What artist would you like to search for? ");
				String artist = scanner.nextLine();
				library.mS_SearchSongByArtist(artist);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 9) {
				System.out.println("Please put in the name of the album you'd like to search for: ");
				String albTitle = scanner.nextLine();
				library.mS_SearchAlbumByTitle(albTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 10) {
				System.out.println("Which artist's album would you like to search for? ");
				String albArtist = scanner.nextLine();
				library.mS_SearchAlbumByArtist(albArtist);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 11) {
				System.out.println("What's the title of the song you would like to favorite? ");
				String sTitle = scanner.nextLine();
				library.favoriteSong(sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 12) {
				System.out.println("What's the title of the song you'd like to rate? ");
				String sTitle = scanner.nextLine();
				System.out.println("What rating would you like to give it? (Integer number 1-5)");
				int rating = scanner.nextInt();
				library.rateSong(sTitle, rating);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 13) {
				System.out.println("Here are the song titles in your library: ");
				library.getSongTitles();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 14) {
				System.out.println("Here are the artists in your library: ");
				library.getArtists();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 15) {
				System.out.println("Here are the album titles in your library: ");
				library.getAlbumTitles();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 16) {
				System.out.println("Here are the playlists in your library: ");
				library.getPlayLists();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 17) {
				System.out.println("Here are the favorite song titles in your library: ");
				library.getFavoriteSongs();
				if(!(exit())) {
					break;
				}
			}
			else {
				System.out.println("Invalid input. Please choose an integer 1 - 17");
				System.out.println();
				command = 0;
			}
		}
		System.out.println("Thank you for using our library!");
		scanner.close();
	}
	
	private static boolean exit() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Would you like to go back to the main menu? (yes or no)");
		
		String answer = "";
		
		while(!(answer.equalsIgnoreCase("yes")) || !(answer.equalsIgnoreCase("no"))) {
			answer = scanner.nextLine();
			
			if (answer.equalsIgnoreCase("yes")) {
				break;
			}
			else if (answer.equalsIgnoreCase("no")) {
				break;
			}
			else {
				System.out.println("Please type yes or no");
			}
		}
		if (answer.equalsIgnoreCase("yes")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static int getValidIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer 1 - 17.");
            scanner.next();
        }
        return scanner.nextInt();
    }

	public void createPlayList(String title) {
		// check to see if there is already a PlayList with the same name
		ArrayList <String> playLists = library.getPlayLists();
		for (String pTitle: playLists) {
			if (pTitle.equalsIgnoreCase(title)) {
				System.out.println("There already exists a playlist with this name");
				return;
			}
		}
		library.createPlayList(title);
		System.out.println("PlayList added successfully");
	}
	
	public void addSong(String title) {
		ArrayList<Song> songs = mStore.searchSongByTitle(title, false);
		if (songs.size() == 0) {
			System.out.println("This song title is not in the music store, cannot add to library");
		}
		
		else {
			if (songs.size() == 1) {
				library.addSong(songs.getFirst());
				System.out.println("Song added to the library");
			}
			
			else {
				System.out.println("There are multiple songs with this name");
				for (Song s: songs) {
					s.printAllDetails();
				}
				
				System.out.println("Would you like to add all songs? (yes or no)");
				Scanner scanner = new Scanner(System.in);
		    	
		    	int count = 0;
		    	while(count < 1) {
			    	String choice = scanner.nextLine();
			    	
			    	if(choice.equalsIgnoreCase("yes")) {
			    		for (Song s : songs) {
			    			library.addSong(s);
			    		}
			    		System.out.println("All songs added to the library");
			    		count++;
			    	}
			    	
			    	else if (choice.equalsIgnoreCase("no")) {
			    		System.out.println("Which artist's song would you like to add?");
			    		String artistName = scanner.nextLine();
			    		boolean flag = false;
			    		for (Song s: songs) {
			    			if(s.getArtist().equalsIgnoreCase(artistName)){
			    				library.addSong(s);
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
	
	public void addAlbum(String title) {
		ArrayList<Album> alb = mStore.searchAlbumByTitle(title, false);
		if (alb.size() == 0) {
			System.out.println("This album title is not in the music store, cannot add to library");
		}
		else {
			if (alb.size() == 1) {
				library.addAlbum(alb.getFirst());
				for (Song s: alb.get(0).getAlbum()) {
					library.addSong(s);
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
			    		for (Album a: alb) {
			    			library.addAlbum(a);
							for (Song s: a.getAlbum()) {
								library.addSong(s);
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
			    				library.addAlbum(a);
			    				flag = true;
			    				for (Song s: a.getAlbum()) {
			    					library.addSong(s);
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
	
	public void searchPlayList(String title) {
		PlayList p = library.searchPlayList(title);
		
		if (p == null) {
			System.out.println("There is no playlist in the library with this name");
		}
		else {
			p.printPlaylist();
		}
	}
	
	public void addSongToPlayList(String pLTitle, String songTitle) {
		Scanner scanner = new Scanner(System.in);
		
		boolean flag;
		PlayList p = library.searchPlayList(pLTitle);
		if (p != null) {
			flag = true;
		}
		else {
			flag = false;
		}
			if (flag) {
				ArrayList<Song> song = mStore.searchSongByTitle(songTitle, false);
				
				if (song.size() == 0) {
					System.out.println("This song title is not in the music store");
				}
				
				else if (song.size() == 1) {
					library.addSongToPlayList(p, song.getFirst()); // adds to the PlayList
					library.addSong(song.get(0)); // adds to the library song list, if not already in there
					System.out.println("Song added to PlayList and library");
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
								library.addSongToPlayList(p, s); // adds to the PlayList
								library.addSong(s); // adds to the library, if not already there
							}
				    		System.out.println("All songs added to the PlayList and library");
					    }
					    	
					    else if (choice.equalsIgnoreCase("no")) {
					   		System.out.println("Which artist's song would you like to add?");
					   		String artistName = scanner.nextLine();
					   		boolean f = false;
					   		for (Song s: song) {
				    			if(s.getArtist().equalsIgnoreCase(artistName)){
				    				library.addSongToPlayList(p, s); // adds to the PlayList
				    				library.addSong(s); // adds to the library, if not already there
				    				System.out.println("Song added to the Playlist and library");
					    			f = true;
					    		}
					    	}
					    		
					    	if(f == false) {
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
		
			else {
			System.out.println("There is no PlayList with that name in the library");
			System.out.println("Please create a PlayList with that name first, and then add a song to it");
		}
	}
	
	public void removeSongFromPlayList(String pLTitle, String songTitle) {
		Scanner scanner = new Scanner(System.in);
		
		boolean flag;
		PlayList p = library.searchPlayList(pLTitle);
		if (p != null) {
			flag = true;
		}
		else {
			flag = false;
		}
		if (flag) {
			ArrayList<Song> pl = p.getPlayList();
			
			if (pl.size() == 0) {
				System.out.println("This PlayList is empty");
			}
			
			else {
				ArrayList<Song> song = new ArrayList<Song>();
				for (Song s: pl) {
					if(s.getTitle().equalsIgnoreCase(songTitle)) {
						song.add(s);
					}
				}
				
				if (song.size() == 1) {
					library.removeSongFromPlayList(p, song.getFirst());
					System.out.println("Song removed from the PlayList.");
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
								library.removeSongFromPlayList(p, s); // removes from the PlayList
							}
				    		System.out.println("All songs removed from the PlayList");
				    		count++;
				    	}
				    	
				    	else if (choice.equalsIgnoreCase("no")) {
				    		System.out.println("Which artist's song would you like to remove?");
				    		String artistName = scanner.nextLine();
				    		boolean f = false;
				    		for (Song s: song) {
				    			if(s.getArtist().equalsIgnoreCase(artistName)){
				    				library.removeSongFromPlayList(p, s); // removes from the PlayList
				    				System.out.println("Song removed from the Playlist");
				    				f = true;
				    			}
				    		}
				    		
				    		if(f == false) {
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
		
		else {
			System.out.println("There is no PlayList with that name in the library");
		}
	}
	
	public void searchSongByTitle(String title) {
		ArrayList<Song> songList = library.searchSongByTitle(title);
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the library");
		}
		else {
			for (Song song: songList) {
				song.printAllDetails();
				System.out.println(); // so there is a space between each song
			}
		}
	}

}
