package com.example.pradeepsai.topten;

/**
 * Created by pradeepsai on 3/26/16.
 */
public class Application {
    String name;
    String artist;
    String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "" + getName() ;
    }
}
