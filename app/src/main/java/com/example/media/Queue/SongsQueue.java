package com.example.media.Queue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.media.Directory;
import com.example.media.List.Item;
import com.example.media.MainActivity;
import com.example.media.Pager.AllSongsFragment;
import com.example.media.Pager.AllSongsRecyclerViewAdapter;
import com.example.media.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * A fragment representing a list of Items.
 */
public class SongsQueue extends Fragment{


    Directory directory;

    public SongsQueue(Directory directory) {
        this.directory=directory;
    }

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    SongsQueueRecyclerViewAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        adapter =new SongsQueueRecyclerViewAdapter(directory.getQueue(),directory.getPlayPos());
    }

    RecyclerView recyclerView ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs_queue_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
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