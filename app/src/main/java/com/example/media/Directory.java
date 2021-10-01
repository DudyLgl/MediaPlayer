package com.example.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.media.List.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Directory implements Serializable, Parcelable {

    private ArrayList<Item> queue;
    private int cover;
    private String name;
    private String artist;
    private int playPos;

    public Directory(ArrayList<Item> queue,String name){
        this.queue=queue;
        this.name=name;
        this.playPos=-1;
    }


    public Directory(ArrayList<Item> queue,String name,String artist,int cover){
        this.queue=queue;
        this.name=name;
        this.artist=artist;
        this.cover=cover;
        this.playPos=-1;
    }

    protected Directory(Parcel in) {
        cover = in.readInt();
        name = in.readString();
        playPos = in.readInt();
    }

    public static final Creator<Directory> CREATOR = new Creator<Directory>() {
        @Override
        public Directory createFromParcel(Parcel in) {
            return new Directory(in);
        }

        @Override
        public Directory[] newArray(int size) {
            return new Directory[size];
        }
    };

    public Directory() {

    }

    public int getPlayPos(){return playPos;}

    public void setPlayPos(int playPos){
        this.playPos=playPos;
    }


    public ArrayList<Item> getQueue() {
        return queue;
    }

    public Directory setQueue(List<Item> queue) {
        this.queue = (ArrayList<Item>) queue;
        return null;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cover);
        dest.writeString(name);
        dest.writeInt(playPos);
    }
}
