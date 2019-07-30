package com.example.malut.javaandroidapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.malut.javaandroidapp.fragments.InfoFragment;
import com.example.malut.javaandroidapp.model.Track;

public class InfoActivity extends AppCompatActivity {

    private Track track;
    private Fragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        infoFragment = (InfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_info);

        track = getIntent().getParcelableExtra("track_info");

        ((InfoFragment) infoFragment).displayInfo(track);

    }
}
