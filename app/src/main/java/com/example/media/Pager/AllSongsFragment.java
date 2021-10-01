package com.example.media.Pager;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.media.Directory;
import com.example.media.List.Item;
import com.example.media.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllSongsFragment extends Fragment{

    PassData callback;

    public void onItemSelectedListener(PassData callback)
    {
        this.callback=callback;
    }


    public interface PassData {
        void OnDataReceived(Directory directory);
    }


    ArrayList<Item> items;

    Directory AllSongs;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;


    AllSongsRecyclerViewAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        Bundle bundle=getArguments();
        AllSongs=bundle.getParcelable("data");
        items=AllSongs.getQueue();

        adapter=new AllSongsRecyclerViewAdapter(items);

        adapter.setOnItemClickListener(new AllSongsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AllSongs.setPlayPos(position);
                callback.OnDataReceived(AllSongs);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        Context context = view.getContext();

        // Set the adapter

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        recyclerView.setAdapter(adapter);

        return view;
    }




}