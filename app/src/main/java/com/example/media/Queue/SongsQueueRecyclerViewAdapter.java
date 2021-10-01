package com.example.media.Queue;

import android.graphics.Color;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media.List.Item;
import com.example.media.Pager.AllSongsRecyclerViewAdapter;
import com.example.media.R;

import java.util.ArrayList;

public class SongsQueueRecyclerViewAdapter extends RecyclerView.Adapter<SongsQueueRecyclerViewAdapter.ViewHolder>{

    private final ArrayList<Item> mValues;
    int highLitPos;

    public void highLightSet(int pos){
        int s=highLitPos;
        highLitPos=pos;
        notifyItemChanged(s);
        notifyItemChanged(pos);
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public SongsQueueRecyclerViewAdapter(ArrayList<Item> items,Integer highLitPos) {
        mValues = items;
        this.highLitPos=highLitPos;
    }

    @Override
    public SongsQueueRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new com.example.media.Queue.SongsQueueRecyclerViewAdapter.ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(final com.example.media.Queue.SongsQueueRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.name.setText(mValues.get(position).getName());
        holder.artist.setText(mValues.get(position).getArtist());
        holder.duration.setText(mValues.get(position).getDuration());
        holder.art.setImageURI(mValues.get(position).getAlbumArt());

        if(highLitPos==position){
            holder.backg.setBackgroundColor(Color.parseColor("#FF9793A8"));
        }

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
        ConstraintLayout backg;
        ImageView art;

        public ViewHolder(View view,final OnItemClickListener listener) {
            super(view);
            name=(TextView) view.findViewById(R.id.title);
            artist=(TextView) view.findViewById(R.id.artist);
            duration=(TextView) view.findViewById(R.id.duration);
            play=(ImageView) view.findViewById(R.id.play);
            backg=(ConstraintLayout) view.findViewById(R.id.item);
            art =(ImageView) view.findViewById(R.id.image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        listener.onItemClick(getAdapterPosition());

                        int s =highLitPos;
                        highLitPos = getAdapterPosition();

                        notifyItemChanged(s);
                        notifyItemChanged(getAdapterPosition());
                    }
                }
            });
        }



    }

}


