package com.example.malut.javaandroidapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.malut.javaandroidapp.adapters.ViewPagerAdapter;
import com.example.malut.javaandroidapp.fragments.InfoFragment;
import com.example.malut.javaandroidapp.fragments.ListAllFragment;
import com.example.malut.javaandroidapp.model.ErrorResponse;
import com.example.malut.javaandroidapp.model.ITunesResponse;
import com.example.malut.javaandroidapp.model.OnTrackInfoPass;
import com.example.malut.javaandroidapp.model.Track;
import com.example.malut.javaandroidapp.services.ApiCallback;
import com.example.malut.javaandroidapp.services.RestClient;
import com.example.malut.javaandroidapp.utils.Consts;
import com.example.malut.javaandroidapp.utils.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
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
    private Database database;
    private Cursor cursor;
    private Bundle bundle;

   private Toolbar toolbar;
   private ProgressBar progressBar;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Objects.requireNonNull(getSupportActionBar()).setTitle("Tracks list");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tracks list");
        setSupportActionBar(toolbar);
//        progressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        database = new Database(this);
        database.open();

        listAll = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_ALL_NAME));
        listByTrack = new ListAllFragment();
        listByArtist = new ListAllFragment();

//        listAll = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_ALL_NAME));
//        listByTrack = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_BY_TRACK_NAME));
//        listByArtist = ListAllFragment.newInstance(getCashedData(database, Consts.DB_TABLE_BY_ARTIST_NAME));


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
                    if(query == null){
                        listByTrack.fillListWithResult(getCashedData(database, Consts.DB_TABLE_BY_TRACK_NAME));
                    } else
                        loadRepos(query, KEY_BY_TRACK, listByTrack, Consts.DB_TABLE_BY_TRACK_NAME);
                } else if (viewPager.getCurrentItem() == 2){
                    if(query == null){
                        listByArtist.fillListWithResult(getCashedData(database, Consts.DB_TABLE_BY_ARTIST_NAME));
                    } else
                        loadRepos(query, KEY_BY_ARTIST, listByArtist, Consts.DB_TABLE_BY_ARTIST_NAME);
                } else {
                    if(query == null){
                        listAll.fillListWithResult(getCashedData(database, Consts.DB_TABLE_ALL_NAME));
                    } else
                        loadRepos(query, null, listAll, Consts.DB_TABLE_ALL_NAME);
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
        if (viewPager.getCurrentItem() == 1){
            loadRepos(query, KEY_BY_TRACK, listByTrack, Consts.DB_TABLE_BY_TRACK_NAME);
        } else if (viewPager.getCurrentItem() == 2){
            loadRepos(query, KEY_BY_ARTIST, listByArtist, Consts.DB_TABLE_BY_ARTIST_NAME);
        } else
            loadRepos(query, null, listAll, Consts.DB_TABLE_ALL_NAME);
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
//        progressBar = (ProgressBar) menu.findItem(R.id.progress).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public void loadRepos(String name, String term, final ListAllFragment fragment, final String table) {

        RestClient.getsInstance()
                .getService()
                .getSongsRepos(name, term)
                .enqueue(new ApiCallback<ITunesResponse>() {
                    @Override
                    public void success(Response<ITunesResponse> response) {
                        if (response.body().getTracks()!= null){
                            database.clearData(table);
                            database.addApiData(response.body().getTracks(), table);
                            fragment.fillListWithResult(response.body().getTracks());
                        } else
                            fragment.fillListWithResult(getCashedData(database, table));

                    }
                    @Override
                    public void failure(ErrorResponse gitRepoError) {
//                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    private ArrayList<Track> fromCursorToList (Cursor cursor){
        ArrayList<Track> tr = new ArrayList<>();
        while(cursor.moveToNext()) {

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
    private ArrayList<Track> getCashedData(Database db, String table){
        cursor = db.getAllData(table);
        return fromCursorToList(cursor);
    }

    public void showProgressBar(SearchView searchView, Context context)
    {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search", null, null);
        if (searchView.findViewById(id).findViewById(R.id.search_progress_bar) != null)
            searchView.findViewById(id).findViewById(R.id.search_progress_bar).animate().setDuration(200).alpha(1).start();

        else
        {
            View v = LayoutInflater.from(context).inflate(R.layout.loading_icon, null);
            ((ViewGroup) searchView.findViewById(id)).addView(v, 1);
        }
    }
    public void hideProgressBar(SearchView searchView)
    {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        if (searchView.findViewById(id).findViewById(R.id.search_progress_bar) != null)
            searchView.findViewById(id).findViewById(R.id.search_progress_bar).animate().setDuration(200).alpha(0).start();
    }
}
