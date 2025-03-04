package view;

import model.*;
import java.util.Scanner;

public class View {
	public static void main(String[] args) {
		LibraryModel library = new LibraryModel();
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
				library.createPlayList(pName);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 2) {
				System.out.println("What song would you like to add?");
				String sTitle = scanner.nextLine();
				library.addSong(sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 3) {
				System.out.println("What album would you like to add?");
				String albName = scanner.nextLine();
				library.addAlbum(albName);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 4) {
				System.out.println("What playlist would you like to search for?");
				String pName = scanner.nextLine();
				library.searchPlayList(pName);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 5) {
				System.out.println("Please put in the playlist you'd like to add to: ");
				String pName = scanner.nextLine();
				System.out.println("Please put in the name of the song you'd like to add: ");
				String sTitle = scanner.nextLine();
				library.addSongToPlayList(pName, sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 6) {
				System.out.println("Please put in the playlist you'd like to remove from: ");
				String pName = scanner.nextLine();
				System.out.println("Please put in the name of the song you'd like to remove: ");
				String sTitle = scanner.nextLine();
				library.removeSongFromPlayList(pName, sTitle);
				if(!(exit())) {
					break;
				}
			}
			else if (command == 7) {
				System.out.println("What song would you like to search for? ");
				String sTitle = scanner.nextLine();
				library.mS_SearchSongByTitle(sTitle);
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
		
		String answer = scanner.nextLine();
		
		while(!(answer.equalsIgnoreCase("yes")) || !(answer.equalsIgnoreCase("no"))) {
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
}
