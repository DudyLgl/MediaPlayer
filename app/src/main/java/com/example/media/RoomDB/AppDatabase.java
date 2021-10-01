package com.example.media.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.media.List.Item;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
}
