package com.example.media.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.media.List.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;


@Dao
public interface ItemDao {
    @Query("SELECT * FROM items")
    List<Item> getAll();

    @Query("SELECT count(*) FROM items")
     public int getSize();



    @Insert
    void insertAll(ArrayList<Item> queue);
}
