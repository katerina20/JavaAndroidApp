package com.example.malut.javaandroidapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.example.malut.javaandroidapp.Fragments.InfoFragment;
import com.example.malut.javaandroidapp.Fragments.ListFragment;
import com.example.malut.javaandroidapp.Model.OnTrackInfoPass;
import com.example.malut.javaandroidapp.Model.Track;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnTrackInfoPass, SearchView.OnQueryTextListener {


    private ListFragment listFragment;
    private boolean inLandscapeMode;
    private InfoFragment infoFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Tracks list");

        inLandscapeMode = findViewById(R.id.fragment_info) != null;
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);


    }

    @Override
    public void onTrackInfoPass(Track track) {
        if (inLandscapeMode) {
            infoFragment.displayInfo(track);
        } else {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            intent.putExtra("track_info", track);
            startActivity(intent);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Looooog", query);
        searchView.clearFocus();
        listFragment.loadRepos(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;


    }
}
