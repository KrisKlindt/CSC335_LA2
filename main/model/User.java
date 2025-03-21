package model;

import java.security.SecureRandom;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import database.MusicStore;

public class User {
	private String userName;
	private String hashedPassword;
	private String saltString;
	private byte[] salt;
	public LibraryModel library = new LibraryModel();
	private MusicStore mS = new MusicStore();

	// Used when first creating a user
	public User(String uName, String password) {
		this.userName = uName;
		
		byte [] s = generateSalt();
		this.salt = s;
		try {
			this.saltString = Base64.getEncoder().encodeToString(salt);
			this.hashedPassword = hashedToString(password, s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	// Used when starting up View, so that existing users can be created again
	// From existing user txt files and put into UserRegistry
	public User(String uName) {
		// Should only take username, hashedPassword, saltString, and salt from the txt file
		try {
			String fp = "./main/database/users/"+ uName +".txt";
			BufferedReader in = new BufferedReader(new FileReader(fp));
			
			String line = in.readLine();
            String[] l = line.split(",");
            
            this.userName = l[0];
            this.hashedPassword = l[1];
            this.saltString = l[2];
            this.salt = Base64.getDecoder().decode(saltString);
            
            in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
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
	
	public String getSaltString() {
		return saltString;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
	public String getUName() {
		return userName;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	// called when a user logs in in the View, so that we don't try to fill in each library
	// of each user in the registry, since only one will be logged in at a time
	public void fillLibraryFromTxt() {
		try {
			String fp = "./main/database/users/"+ userName +".txt";
			BufferedReader in = new BufferedReader(new FileReader(fp));
			
			String line;
			// Goes through username.txt to get library info
            while ((line = in.readLine()) != null) {
                String[] l = line.split(",");
                if (l[0].equals("Album")) {
                	// Create Album, add songs to it
                	Album alb = new Album(l[1], l[2], l[3], l[4]);;
                    ArrayList<Song> songList = new ArrayList<Song>(); // used to keep track of all songs in the albums
                	
                	for (int i=5; i < l.length; i++) {
                		String[] songInfo = l[i].split("_"); // title_artist
                		ArrayList<Song> sngs = mS.searchSongByTitle(songInfo[0], false);
                		ArrayList<Song> sngsByArtist = mS.searchSongByArtist(songInfo[1], false);
                		
                		for(Song s: sngs) {
                			if (sngsByArtist.contains(s)) {
                				s.setPlays(Integer.parseInt(songInfo[2]));
                				s.rateSong(Integer.parseInt(songInfo[3]));
                				if(songInfo[4].equals("true")) {
                					s.markAsFavorite();
                				}
                    			alb.addSong(s); // ensures song has both the right title and artist
                    			songList.add(s);
                			}
                		}
                	}
                	library.addAlbum(alb);
                	// since library's addAlbum does not also add each song in the album (this is done in the view)
                	// the songs need to be added to the library separately here as well
                	for (Song s: songList) {
                		library.addSong(s);
                	}
                }
                
                else if (l[0].equals("PlayList")) {
                	// Create PlayList, add songs to it
                	library.createPlayList(l[1]);
                	
                	for (int i=2; i < l.length; i++) {
                		String[] songInfo = l[i].split("_"); // title_artist
                		ArrayList<Song> sngs = mS.searchSongByTitle(songInfo[0], false);
                		ArrayList<Song> sngsByArtist = mS.searchSongByArtist(songInfo[1], false);
                		
                		for(Song s: sngs) {
                			if (sngsByArtist.contains(s)) {
                				s.setPlays(Integer.parseInt(songInfo[2]));
                				s.rateSong(Integer.parseInt(songInfo[3]));
                				if(songInfo[4].equals("true")) {
                					s.markAsFavorite();
                				}
                    			library.addSongToPlayList(library.searchPlayList(l[1]), s); // ensures song has both the right title and artist
                			}
                		}
                	}
                }
                else {
                	// is the first line with user login information, do nothing
                }
            }
            
            in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// called in View when the user wants to save their data, i.e. when they exit
	public void saveUserToFile() {
		// Should write to the uName.txt everything in the current library of the user
		try {
	        String file = "./main/database/users/" + getUName() + ".txt";
	        
	        // Should write to username.txt
	        String content = getUName() + "," + getHashedPassword() + "," + 
	        		getSaltString(); // Keep initial line the same
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	            writer.write(content);
	            writer.newLine();
	            
	            // go through user's library, add all information from it in way that can be read back in
	            // Albums
	            // Since the associated album is added to the library when a song is added, all the songs in the LM
	            // will be contained within an album, so no need to have a separate section for songs here
	            ArrayList<Album> albs = library.getAlbums();
	            for(Album a: albs) {
	            	content = "Album," + a.getTitle() + "," + a.getArtist() + "," + a.getGenre() + "," + a.getYear() + ",";
	            	// go through the song in each album, add title to content
	            	for (Song s: a.getAlbum()) {
	            		content = content + s.getTitle() + "_" + s.getArtist() + "_" + s.getPlays() + "_" + 
	            					s.getRating() + "_" + s.getFavorite(); // need both title and artist to ensure uniqueness
	            	}
	            	content = content.substring(0, content.length()-1); // gets rid of last ,
	            	writer.write(content);
		            writer.newLine();
	            }
	            
	            // PlayLists
	            ArrayList<PlayList> playLists = library.getPLs();
	            for(PlayList p: playLists) {
	            	content = "PlayList," + p.getTitle() + ",";
	            	// go through the song in each album, add title to content
	            	for (Song s: p.getPlayList()) {
	            		content = content + s.getTitle() + "_" + s.getArtist() + "_" + s.getPlays() + "_" + 
            					s.getRating() + "_" + s.getFavorite(); // need both title and artist to ensure uniqueness
	            	}
	            	content = content.substring(0, content.length()-1); // gets rid of last ,
	            	writer.write(content);
		            writer.newLine();
	            }
	        } 
		}
		catch (IOException e) {
	         e.printStackTrace();
	    }
	}
}
