package com.example.malut.javaandroidapp.Model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.malut.javaandroidapp.Listeners.OnTrackClickListener;
import com.example.malut.javaandroidapp.R;

import java.util.ArrayList;

public class TrackRecyclerAdapter extends RecyclerView.Adapter<TrackRecyclerAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Track> tracks;
    private OnTrackClickListener listener;

    public TrackRecyclerAdapter(Activity context, ArrayList<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
    }

    @Override
    public TrackRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrackRecyclerAdapter.ViewHolder viewHolder, int i) {

        Track selectedTrack = tracks.get(i);

        viewHolder.textViewName.setText(selectedTrack.getTrackName());
        viewHolder.textViewSurname.setText(selectedTrack.getArtistName());
        Glide.with(viewHolder.imageView)
                .load(selectedTrack.getTrackImage())
                .into(viewHolder.imageView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewSurname;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textName);
            textViewSurname = itemView.findViewById(R.id.textSurname);
            imageView = itemView.findViewById(R.id.imageUser);
        }
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public OnTrackClickListener getListener() {
        return listener;
    }

    public void setListener(OnTrackClickListener listener) {
        this.listener = listener;
    }

}
