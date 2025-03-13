package model;

import java.util.ArrayList;

public class PlayList {
	private String title;
    private ArrayList<Song> songs;
    
    public PlayList(String title) {
    	this.title = title;
        this.songs = new ArrayList<>();
    }
    
    public String getTitle() {
    	return title;
    }
    
    public ArrayList<Song> getPlayList() {
    	ArrayList<Song> pl = new ArrayList<Song>(songs);
    	return pl;
    }
    
    public void addSong(Song song) {
    	if (!(songs.contains(song))) {
            songs.add(song);
    	}
    }
    
    public void removeSong(Song song) {
    	songs.remove(song);
    }
    
    public void printPlaylist() {
    	System.out.println(title + ":");
    	for (Song song:songs) {
    		System.out.println(song.getTitle() + " by " + song.getArtist());
    	}
    }
}
