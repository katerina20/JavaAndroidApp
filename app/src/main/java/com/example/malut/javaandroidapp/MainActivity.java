package com.example.malut.javaandroidapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.example.malut.javaandroidapp.adapters.ViewPagerAdapter;
import com.example.malut.javaandroidapp.fragments.InfoFragment;
import com.example.malut.javaandroidapp.fragments.ListAllFragment;
import com.example.malut.javaandroidapp.model.ErrorResponse;
import com.example.malut.javaandroidapp.model.ITunesResponse;
import com.example.malut.javaandroidapp.model.OnTrackInfoPass;
import com.example.malut.javaandroidapp.model.Track;
import com.example.malut.javaandroidapp.services.ApiCallback;
import com.example.malut.javaandroidapp.services.RestClient;

import java.util.Objects;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnTrackInfoPass, SearchView.OnQueryTextListener {

    private static String KEY_BY_TRACK = "songTerm";
    private static String KEY_BY_ARTIST = "artistTerm";
    private boolean inLandscapeMode;
    private InfoFragment infoFragment;
    private SearchView searchView;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ListAllFragment listAll, listByTrack, listByArtist;
    private String queryText;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tracks list");

        listAll = new ListAllFragment();
        listByTrack = new ListAllFragment();
        listByArtist = new ListAllFragment();

        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(listAll, "All");
        adapter.addFragment(listByTrack, "By track");
        adapter.addFragment(listByArtist, "By artist");



//        inLandscapeMode = findViewById(R.id.fragment_info) != null;
//        listAllFragment = (ListAllFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_all);

        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.slide_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String query = getQueryText();
                if (viewPager.getCurrentItem() == 1){
                    loadRepos(query, KEY_BY_TRACK, listByTrack);
                } else if (viewPager.getCurrentItem() == 2){
                    loadRepos(query, KEY_BY_ARTIST, listByArtist);
                } else
                    loadRepos(query, null, listAll);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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
        setQueryText(query);
        searchView.clearFocus();
        if (viewPager.getCurrentItem() == 1){
            loadRepos(query, KEY_BY_TRACK, listByTrack);
        } else if (viewPager.getCurrentItem() == 2){
            loadRepos(query, KEY_BY_ARTIST, listByArtist);
        } else
            loadRepos(query, null, listAll);
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

    public void loadRepos(String name, String term, final ListAllFragment fragment) {

        RestClient.getsInstance()
                .getService()
                .getSongsRepos(name, term)
                .enqueue(new ApiCallback<ITunesResponse>() {
                    @Override
                    public void success(Response<ITunesResponse> response) {
                        fragment.fillListWithResult(response.body().getTracks());
                    }
                    @Override
                    public void failure(ErrorResponse gitRepoError) {

                    }
                });

    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
}
