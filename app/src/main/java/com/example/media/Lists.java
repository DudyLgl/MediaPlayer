package com.example.media;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.media.List.Item;

import java.util.ArrayList;
import java.util.Collections;

public class Lists {
    private Directory allSongs;

    public int compare(Item item1, Item item2) {
        return item1.getArtist().compareTo(item2.getArtist());
    }

    public void setAllSongs(Directory allSongs) {
        this.allSongs = allSongs;
    }

    public Lists(Context context) {
        ArrayList<Item> songs =new ArrayList<Item>();
        Cursor cursor;

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection ={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
        };

        cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC, " +
                        "LOWER(" + MediaStore.Audio.Media.ARTIST + ") ASC, " +
                        "LOWER(" + MediaStore.Audio.Media.ALBUM + ") ASC");

        while(cursor.moveToNext()){
            int mns = (cursor.getInt(4) / 60000) % 60000;
            int scs = cursor.getInt(4) % 60000 / 1000;

            String duration = String.format("%02d:%02d",  mns, scs);


            songs.add(new Item(
                    cursor.getString(2),
                    cursor.getString(1),
                    duration,
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cursor.getLong(0)),
                    cursor.getString(5),
                    ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), cursor.getLong(6))
            ));
        }
        allSongs=new Directory(songs,"AllSongs");

        projection = new String[] {
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS};

        Cursor cursor1 = context.getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        cursor.moveToFirst();
        while(cursor1.moveToNext()){
            Log.i("AA", "Lists: "+cursor.getString(0)+","+cursor.getString(1));//+","+cursor.getString(2)+","+cursor.getString(3)+","+cursor.getString(4));
        }

        songs=allSongs.getQueue();

        Collections.sort(songs,this::compare);

    }
    public Lists(Directory directory) {

        allSongs=directory;

        ArrayList<Item> songs =new ArrayList<Item>();

        songs=allSongs.getQueue();

        Collections.sort(songs,this::compare);

    }

    public Directory getSongs(){
        return allSongs;
    }
}
