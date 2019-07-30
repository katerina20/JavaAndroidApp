package com.example.malut.javaandroidapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.malut.javaandroidapp.fragments.InfoFragment;
import com.example.malut.javaandroidapp.model.Track;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    private Track track;
    private Fragment infoFragment;
    private boolean inLandscapeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        inLandscapeMode = findViewById(R.id.fragment_list) != null;

        if(inLandscapeMode){


        } else{
            infoFragment = (InfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        }

        track = getIntent().getParcelableExtra("track_info");

        ((InfoFragment) infoFragment).displayInfo(track);


    }
}
