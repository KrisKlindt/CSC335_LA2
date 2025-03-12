package view;

import model.*;
import database.*;
import java.util.Scanner;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class View {
	
	private LibraryModel library;
	private MusicStore mStore;
	private UserRegistry uR;
	private User u;
	
	public View() {
		this.library = new LibraryModel();
		this.uR = new UserRegistry();
		this.mStore = new MusicStore();
	}
	
	public static void main(String[] args) {
		View view = new View();
		boolean isLoggedIn = view.login();
		if (isLoggedIn) {
			view.run();
		}
	}
	
	public boolean login() {
		Scanner scanner = new Scanner(System.in);
		int command = 0;
		boolean loggedIn = true;
		
		while (command < 3) {
			System.out.println("Welcome User!\nWould you like to login, or create a new account?");
			System.out.println("1. Login");
			System.out.println("2. Create a new account");
			
			System.out.println("Please enter the integer of the command you'd like to use: ");
			System.out.println("Or enter a negative integer to exit");
			
			command = getValidIntegerInput(scanner);
			scanner.nextLine();
			
			if (command < 0) {
				System.out.println("You have exitted successfully.");
				loggedIn = false;
				break;
			}
			if (command == 1) {
				System.out.println("What is your user name?");
				String uName = scanner.nextLine();
				String noSpaces = uName.replace(" ", "_");
				// Since I plan on using the user name as part of a unique file name, need to replace spaces
				
				if(uR.checkIfUserExists(noSpaces)) {
					User potentialUser = uR.getUser(noSpaces);
					
					System.out.println("What is your password?");
					
					int count = 0;
					boolean flag = false;
					while (count<=3) {
						String pwd = scanner.nextLine();
						try {
							if (potentialUser.getHashedPassword().equals(hashedToString(pwd, potentialUser.getSalt()))) {
								System.out.println("Succesfully logged in!");
								this.u = potentialUser;
								u.fillLibraryFromTxt();
								flag = true;
								break;
							}
							else if (count == 2){
								System.out.println("You have one more chance to enter the correct password, try again.");
								count++;
							}
							else if (count == 3) {
								count++;
							}
							else {
								System.out.println("Incorrect password for this user name, try again.");
								count++;
							}
							
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
					}
					// since the break within the inner count<3 while loop only breaks out of that one,
					// need to break again if the user successfully logged in
					if (flag) {
						break;
					}
					// User entered wrong password 3 times, should kick them back to start
					else {
						System.out.println("You entered the wrong password 3 times, you will now be taken "
								+ "back to the starting message.");
						command = 0;
					}
				}
				else {
					System.out.println("This user name does not exist.");
					System.out.println("Create an account with this user name, or check that"
							+ " the spelling is correct (User name is case sensitive).");
				}
			}
			else if (command == 2) {
				System.out.println("What user name would you like to use?");
				boolean flag = true;
				String userName = "";
				
				while(flag) {
					String uName = scanner.nextLine();
					String noSpaces = uName.replace(" ", "_");
					// Since I plan on using the user name as part of a unique file name, need to replace spaces
					if(uR.checkIfUserExists(noSpaces)) {
						System.out.println("This user name is taken, please choose another one.");
					}
					else {
						userName = noSpaces;
						flag = false;
					}
				}
				System.out.println("What password would you like to use?");
				String pWord = scanner.nextLine();
				this.u = new User(userName,pWord);
				uR.addUser(u);
				break;
			}
			else {
				System.out.println("Invalid input. Please choose an integer 1 or 2");
				command = 0;
			}
		}
		return loggedIn;
	}
	
	private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return hashedPassword;
    }
	
	private String hashedToString(String password, byte[] s) throws NoSuchAlgorithmException {
        byte[] hashedPassword = hashPassword(password, s);
        byte[] saltAndHash = new byte[s.length + hashedPassword.length];
        System.arraycopy(s, 0, saltAndHash, 0, s.length);
        System.arraycopy(hashedPassword, 0, saltAndHash, s.length, hashedPassword.length);
        return Base64.getEncoder().encodeToString(saltAndHash);
    }
	
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome "+ u.getUName().replace("_", " ") +"!\nThis is your music library where you can add songs and Albums that are available within our store!");
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
				searchSongByArtist(artist);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 9) {
				System.out.println("Please put in the name of the album you'd like to search for: ");
				String albTitle = scanner.nextLine();
				searchAlbumByTitle(albTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 10) {
				System.out.println("Which artist's album would you like to search for? ");
				String albArtist = scanner.nextLine();
				searchAlbumByArtist(albArtist);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 11) {
				System.out.println("What's the title of the song you would like to favorite? ");
				String sTitle = scanner.nextLine();
				favoriteSong(sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 12) {
				System.out.println("What's the title of the song you'd like to rate? ");
				String sTitle = scanner.nextLine();
				System.out.println("What rating would you like to give it? (Integer number 1-5)");
				int rating = scanner.nextInt();
				rateSong(sTitle, rating);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 13) {
				System.out.println("Here are the song titles in your library: ");
				getSongTitles();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 14) {
				System.out.println("Here are the artists in your library: ");
				getArtists();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 15) {
				System.out.println("Here are the album titles in your library: ");
				getAlbumTitles();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 16) {
				System.out.println("Here are the playlists in your library: ");
				getPlayLists();
				if(!(exit())) {
					break;
				}
			}
			else if (command == 17) {
				System.out.println("Here are the favorite song titles in your library: ");
				getFavoriteSongs();
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
				    		count++;
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
				
				else if (song.size() == 0) {
					System.out.println("There are no songs with this name in the PlayList");
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
		ArrayList<Song> songList = mStore.searchSongByTitle(title, false);
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the music store");
		}
		else {
			for (Song song: songList) {
				song.printAllDetails();
				System.out.println(); // so there is a space between each song
			}
		}
	}
	
	public void searchSongByArtist(String artist) {
		ArrayList<Song> songList = mStore.searchSongByArtist(artist, false);
		
		if (songList.size() == 0) {
			System.out.println("This song artist is not in the music store");
		}
		else {
			for (Song song: songList) {
				song.printAllDetails();
				System.out.println(); // so there is a space between each song
			}
		}
	}
	
	public void searchAlbumByTitle(String title) {
		ArrayList<Album> albumList = mStore.searchAlbumByTitle(title, false);
		
		if (albumList.size() == 0) {
			System.out.println("This album title is not in the music store");
		}
		else {
			for (Album alb: albumList) {
				alb.printAlbumDetails();
				System.out.println(); // so there is a space between each album
			}
		}
	}
	
	public void searchAlbumByArtist(String artist) {
		ArrayList<Album> albumList = mStore.searchAlbumByArtist(artist, false);
		
		if (albumList.size() == 0) {
			System.out.println("This album artist is not in the music store");
		}
		else {
			for (Album alb : albumList) {
				alb.printAlbumDetails();
				System.out.println(); // so there is a space between each album
			}
		}
	}
	
	public void favoriteSong(String title) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Song> songList = library.searchSongByTitle(title);
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the library");
		}
		
		else if (songList.size() == 1){
			library.favoriteSong(songList.getFirst());;
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
						library.favoriteSong(s);
					}
		    		System.out.println("All songs favorited");
		    		count++;
		    	}
		    	
		    	else if (choice.equalsIgnoreCase("no")) {
		    		System.out.println("Which artist's song would you like to favorite?");
		    		String artistName = scanner.nextLine();
		    		boolean f = false;
		    		for (Song s: songList) {
		    			if(s.getArtist().equalsIgnoreCase(artistName)){
		    				library.favoriteSong(s);
		    				System.out.println("Song favorited");
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
	
	public void rateSong(String title, int rating) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Song> songList = library.searchSongByTitle(title);
		
		if (songList.size() == 0) {
			System.out.println("This song title is not in the library");
		}
		
		else if (songList.size() == 1){
			library.rateSong(songList.getFirst(), rating);
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
						library.rateSong(s, rating);;
					}
		    		System.out.println("All songs rated");
		    		count++;
		    	}
		    	
		    	else if (choice.equalsIgnoreCase("no")) {
		    		System.out.println("Which artist's song would you like to rate?");
		    		String artistName = scanner.nextLine();
		    		boolean f = false;
		    		for (Song s: songList) {
		    			if(s.getArtist().equalsIgnoreCase(artistName)){
		    				library.rateSong(s, rating);;
		    				System.out.println("Song rated");
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
	
	public void getSongTitles(){
		ArrayList<String> titles = library.getSongTitles();
		
		for (String title: titles) {
			System.out.println(title);
			System.out.println(); // for a space between each song
		}
	}
	
	public void getArtists(){
		ArrayList<String> artists = library.getArtists();
		
		for (String artist: artists) {
			System.out.println(artist);
			System.out.println(); // for a space between artists
			}
	}

	
	public void getAlbumTitles(){
		ArrayList<String> albumTitles = library.getAlbumTitles();
		
		for (String title: albumTitles) {
			System.out.println(title);
			System.out.println(); // for a space between each album
		}
	}
	
	public void getPlayLists(){
		ArrayList<String> pls = library.getPlayLists();
		
		for (String title: pls) {
			System.out.println(title);
			System.out.println(); // for a space between each play list
		}
	}
	
	public ArrayList<String> getFavoriteSongs(){
		ArrayList<String> titles = library.getFavoriteSongs();
		
		for (String title: titles) {
			System.out.println(title);
			System.out.println(); // for a space between each song
		}
		
		return titles;
	}
}
