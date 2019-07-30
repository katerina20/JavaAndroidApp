package com.example.malut.javaandroidapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.malut.javaandroidapp.R;
import com.example.malut.javaandroidapp.listeners.OnTrackClickListener;
import com.example.malut.javaandroidapp.model.Track;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        if (i == tracks.size() - 1) {
            viewHolder.divider.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.divider.setVisibility(View.VISIBLE);
        }

        Track selectedTrack = tracks.get(i);

        viewHolder.trackName.setText(selectedTrack.getTrackName());
        viewHolder.artistName.setText(selectedTrack.getArtistName());
        Glide.with(viewHolder.trackImage)
                .load(selectedTrack.getTrackImage())
                .into(viewHolder.trackImage);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.track_name_list)
        TextView trackName;

        @BindView(R.id.artist_name_list)
        TextView artistName;

        @BindView(R.id.track_image_list)
        ImageView trackImage;

        @BindView(R.id.divider)
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
