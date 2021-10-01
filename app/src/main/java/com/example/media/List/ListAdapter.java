package com.example.media.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.media.List.Item;
import com.example.media.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item =getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        TextView tittle=(TextView) convertView.findViewById(R.id.title);
        TextView artist=(TextView) convertView.findViewById(R.id.artist);
        TextView duration=(TextView) convertView.findViewById(R.id.duration);
        //ImageView play=(ImageView) convertView.findViewById(R.id.play);

        tittle.setText(item.getName());
        artist.setText(item.getArtist());
        duration.setText(String.valueOf(item.getDuration()));
        //play.setImageResource(R.drawable.more_vert_24);

        return convertView;
    }


}
