package model;

import java.util.ArrayList;
import java.util.Collections;

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
    
    public void addToFront(Song song) {
    	songs.add(0, song);
    }
    
    public void removeSong(Song song) {
    	songs.remove(song);
    }
    
    public void length10() {
    	if (songs.size() > 10) { // ensure only 10 songs are kept
            songs.remove(songs.size() - 1);
        }
    }
    
    public void shuffleSongs() {
    	Collections.shuffle(songs);
    }
    
    public void printPlaylist() {
    	System.out.println(title + ":");
    	for (Song song:songs) {
    		System.out.println(song.getTitle() + " by " + song.getArtist());
    	}
    }
    
    @Override
   	public boolean equals(Object o) {
   		if(o == null) return false;
   		if(o == this) return true;
   		if(o.getClass() != this.getClass()) return false;
   		return ((PlayList)o).title.equals(this.title);
   	}
}
