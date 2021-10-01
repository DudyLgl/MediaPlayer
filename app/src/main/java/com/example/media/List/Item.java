package com.example.media.List;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    private final Uri uri;

    @ColumnInfo(name = "uri")
    private final String stringUri;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "artist")
    private final String artist;

    @ColumnInfo(name = "duration")
    private final String duration;

    @ColumnInfo(name = "album")
    private final String album;

    @ColumnInfo(name = "albumart")
    private String stringAlbumArt;

    @Ignore
    private Uri albumArt;

    @Ignore
    boolean playing;


    public Item(String name, String artist, String duration, String album,String stringUri,String stringAlbumArt) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.album = album;

        this.stringUri=stringUri;
        this.uri=Uri.parse(stringUri);

        this.stringAlbumArt=stringAlbumArt;
        this.albumArt=Uri.parse(stringAlbumArt);
    }

    public Item(String name, String artist, String duration, Uri uri, String album, Uri albumArt) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.uri=uri;
        this.stringUri = uri.toString();
        this.stringAlbumArt=albumArt.toString();
        this.album=album;
        this.playing=false;
        this.albumArt=albumArt;
    }


    public int getId()
    {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public Uri getAlbumArt(){ return albumArt;}

    public Uri getUri(){return uri;}


    public String getStringUri() {
        return stringUri;
    }

    public String getStringAlbumArt() {
        return stringAlbumArt;
    }

    public boolean getPlaying(){return playing;}



    public void setPlay(){
        playing=true;
    }
    public void setPause(){
        playing=false;
    }

    public void setId(int id)
    {
        this.id=id;
    }


}
