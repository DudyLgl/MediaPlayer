package com.example.media.Pager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.media.List.Item;
import com.example.media.R;

import java.util.ArrayList;


public class AllSongsRecyclerViewAdapter extends RecyclerView.Adapter<AllSongsRecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    private final ArrayList<Item> mValues;

    public AllSongsRecyclerViewAdapter(ArrayList<Item> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mValues.get(position).getName());
        holder.artist.setText(mValues.get(position).getArtist());
        holder.duration.setText(mValues.get(position).getDuration());
        holder.cover.setImageURI(mValues.get(position).getAlbumArt());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView artist;
        TextView duration;
        ImageView play;
        ImageView cover;

        public ViewHolder(View view) {
            super(view);
            name=(TextView) view.findViewById(R.id.title);
            artist=(TextView) view.findViewById(R.id.artist);
            duration=(TextView) view.findViewById(R.id.duration);
            play=(ImageView) view.findViewById(R.id.play);
            cover=(ImageView) view.findViewById(R.id.image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position =getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }
}
