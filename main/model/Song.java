package model;

public class Song {
    private String title;
    private String artist;
    private String album;
    private String genre;
    private boolean favorite;
    private int rating;
    private int plays;

    public Song(String title, String artist, String album, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.favorite = false;
        this.plays = 0;
    }

    // Only used when reading in the song from txt file
    public void setPlays(int num) {
    	plays=num;
    }
    
    public String getTitle() {
		return title;
	}
    
    public String getArtist() {
    	return artist;
    }
    
    public String getAlbum() {
    	return album;
    }
    
    public String getGenre() {
    	return genre;
    }
    
    public int getRating() {
    	return rating;
    }
    
    public boolean getFavorite() {
    	return favorite;
    }
    
    public int getPlays() {
    	return plays;
    }
    
    public void rateSong(int rating) {
		this.rating = rating;
		this.favorite = (rating == 5);
    }
    
    public void markAsFavorite() {
    	this.favorite = true;
    }
    
    public void removeFavorite() {
    	this.favorite = false;
    }
    
    public boolean equalTo(Song song2) {
    	return (title.equalsIgnoreCase(song2.getTitle()) && 
    			artist.equalsIgnoreCase(song2.getArtist()) && 
    			album.equalsIgnoreCase(song2.getAlbum()));
    }
    
    public void printAllDetails() {
        System.out.println("Title: " + title);
        System.out.println("Artist: " + artist);
        System.out.println("Album: " + album);
    }

    @Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(o.getClass() != this.getClass()) return false;
		return ((Song)o).title.equals(this.title) && 
				((Song)o).artist.equals(this.artist)&&
				((Song)o).album.equals(this.album);
	}
    
    public void play() {
    	this.plays += 1;
    }
    
	// Won't override hashcode, since it is unnecessary for our purposes
}
