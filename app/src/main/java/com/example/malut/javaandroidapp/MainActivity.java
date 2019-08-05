package com.example.malut.javaandroidapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.malut.javaandroidapp.adapters.ViewPagerAdapter;
import com.example.malut.javaandroidapp.fragments.InfoFragment;
import com.example.malut.javaandroidapp.fragments.ListAllFragment;
import com.example.malut.javaandroidapp.model.ErrorResponse;
import com.example.malut.javaandroidapp.model.ITunesResponse;
import com.example.malut.javaandroidapp.model.OnTrackInfoPass;
import com.example.malut.javaandroidapp.model.Track;
import com.example.malut.javaandroidapp.services.ApiCallback;
import com.example.malut.javaandroidapp.services.ApiService;
import com.example.malut.javaandroidapp.services.RestClient;
import com.example.malut.javaandroidapp.utils.Consts;
import com.example.malut.javaandroidapp.utils.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnTrackInfoPass, SearchView.OnQueryTextListener {

    private static String KEY_BY_TRACK = "songTerm";
    private static String KEY_BY_ARTIST = "artistTerm";
    private boolean inLandscapeMode;
    private InfoFragment infoFragment;
    private SearchView searchView;
    private ViewPagerAdapter adapter;

    private ListAllFragment listAll, listByTrack, listByArtist;
    private String queryText;
    private Database database;
    private Cursor cursor;
    private ApiService service;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar_result)
    ProgressBar progressBar;

    @BindView(R.id.slide_tabs)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Tracks list");
        setSupportActionBar(toolbar);
        database = new Database(this);
        database.open();
        service = RestClient.getsInstance().getService();

        if (savedInstanceState == null) {

            listAll = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_ALL_NAME));
            listByTrack = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_BY_TRACK_NAME));
            listByArtist = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_BY_ARTIST_NAME));

        } else {
            listAll = (ListAllFragment) getSupportFragmentManager().getFragment(savedInstanceState, "list_all");
            listByTrack = (ListAllFragment) getSupportFragmentManager().getFragment(savedInstanceState, "list_by_track");
            listByArtist = (ListAllFragment) getSupportFragmentManager().getFragment(savedInstanceState, "list_by_artist");
        }

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(listAll, "All");
        adapter.addFragment(listByTrack, "By track");
        adapter.addFragment(listByArtist, "By artist");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager);

        inLandscapeMode = findViewById(R.id.fragment_info) != null;

        if (inLandscapeMode) {
            infoFragment = (InfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String query = getQueryText();
                if (viewPager.getCurrentItem() == 1) {
                    if (query == null) {
                        new loadDataFromDatabase((ListAllFragment) adapter.getItem(i)).execute(Consts.DB_TABLE_BY_TRACK_NAME);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        loadRepos(query, KEY_BY_TRACK, (ListAllFragment) adapter.getItem(i), Consts.DB_TABLE_BY_TRACK_NAME);
                    }
                } else if (viewPager.getCurrentItem() == 2) {
                    if (query == null) {
                        new loadDataFromDatabase((ListAllFragment) adapter.getItem(i)).execute(Consts.DB_TABLE_BY_ARTIST_NAME);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        loadRepos(query, KEY_BY_ARTIST, (ListAllFragment) adapter.getItem(i), Consts.DB_TABLE_BY_ARTIST_NAME);
                    }
                } else {
                    if (query == null) {
                        new loadDataFromDatabase((ListAllFragment) adapter.getItem(i)).execute(Consts.DB_TABLE_ALL_NAME);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        loadRepos(query, null, (ListAllFragment) adapter.getItem(i), Consts.DB_TABLE_ALL_NAME);
                    }
                }

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
        progressBar.setVisibility(View.VISIBLE);
        if (viewPager.getCurrentItem() == 1) {
            loadRepos(query, KEY_BY_TRACK, (ListAllFragment) adapter.getItem(viewPager.getCurrentItem()), Consts.DB_TABLE_BY_TRACK_NAME);
        } else if (viewPager.getCurrentItem() == 2) {
            loadRepos(query, KEY_BY_ARTIST, (ListAllFragment) adapter.getItem(viewPager.getCurrentItem()), Consts.DB_TABLE_BY_ARTIST_NAME);
        } else
            loadRepos(query, null, (ListAllFragment) adapter.getItem(viewPager.getCurrentItem()), Consts.DB_TABLE_ALL_NAME);
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
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public void loadRepos(String name, String term, final ListAllFragment fragment, final String table) {


        RestClient.getsInstance()
                .getService()
                .getSongsRepos(name, term)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( response -> {
                    if (response.getTracks() != null) {
                        database.clearData(table);
                        database.addApiData(response.getTracks(), table);
                        progressBar.setVisibility(View.GONE);
                        fragment.fillListWithResult(response.getTracks());
                    } else
                        progressBar.setVisibility(View.GONE);
                    new loadDataFromDatabase(fragment).execute(table);
                }, error -> {
                    Log.e("RestApiClient", error.getMessage());
                    makeErrorToast("Result error");
                    progressBar.setVisibility(View.GONE);
                });

    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    private ArrayList<Track> fromCursorToList(Cursor cursor) {
        ArrayList<Track> tr = new ArrayList<>();
        while (cursor.moveToNext()) {

            String trackName = cursor.getString(cursor.getColumnIndex(Consts.DB_COL_TRACK_NAME));
            String artistName = cursor.getString(cursor.getColumnIndex(Consts.DB_COL_ARTIST_NAME));
            String country = cursor.getString(cursor.getColumnIndex(Consts.DB_COL_COUNTRY));
            Date releaseDate = null;
            try {
                releaseDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).parse(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_RELEASE_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int trackId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_ID)));
            String albumName = cursor.getString(cursor.getColumnIndex(Consts.DB_COL_ALBUM_NAME));
            long trackTime = Long.parseLong(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_TRACK_TIME)));
            String genreName = cursor.getString(cursor.getColumnIndex(Consts.DB_COL_GENRE));
            String trackImage = cursor.getString(cursor.getColumnIndex(Consts.DB_COL_TRACK_IMAGE));
            Track track = new Track(trackName, artistName, country, releaseDate, trackId, albumName, trackTime, genreName, trackImage);
            tr.add(track);
        }
        return tr;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private ArrayList<Track> getCashedData(Database db, String table) {
        cursor = db.getAllData(table);
        return fromCursorToList(cursor);
    }

    private void makeErrorToast(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public class loadDataFromDatabase extends AsyncTask<String, Integer, ArrayList<Track>> {

        ListAllFragment fragment;

        public loadDataFromDatabase(ListAllFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        protected ArrayList<Track> doInBackground(String... strings) {
            cursor = database.getAllData(strings[0]);
            return fromCursorToList(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Track> arrayList) {
            super.onPostExecute(arrayList);
            fragment.fillListWithResult(arrayList);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (listAll.isAdded() && listByArtist.isAdded() && listByArtist.isAdded()) {
            fragmentManager.putFragment(outState, "list_all", listAll);
            fragmentManager.putFragment(outState, "list_by_track", listByTrack);
            fragmentManager.putFragment(outState, "list_by_artist", listByArtist);
        }

    }
}
