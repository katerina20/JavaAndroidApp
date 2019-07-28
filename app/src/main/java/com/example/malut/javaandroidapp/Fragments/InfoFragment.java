package com.example.malut.javaandroidapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.malut.javaandroidapp.Model.Track;
import com.example.malut.javaandroidapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.info_track_name)
    TextView trackName;

    @BindView(R.id.artist_name)
    TextView artistName;

    @BindView(R.id.album_name)
    TextView albumName;

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.release_date)
    TextView releaseDate;

    @BindView(R.id.country)
    TextView country;

    @BindView(R.id.genre)
    TextView genre;

    @BindView(R.id.background_view)
    ImageView backView;
    @BindView(R.id.track_image)
    ImageView trackImage;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    public void displayInfo(Track track) {
        trackName.setText(track.getTrackName());
        artistName.setText(track.getArtistName());
        albumName.setText(track.getAlbumName());
        duration.setText(track.getTrackTimeFormatted());
        releaseDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(track.getReleaseDate()));
        country.setText(track.getCountry());
        genre.setText(track.getGenreName());
        Glide.with(this)
                .load(track.getTrackImage())
                .transform(new BlurTransformation(100))
                .into(backView);
        Glide.with(this)
                .load(track.getTrackImage())
                .into(trackImage);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
