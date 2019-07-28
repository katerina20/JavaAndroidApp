package com.example.malut.javaandroidapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.malut.javaandroidapp.Listeners.OnTrackClickListener;
import com.example.malut.javaandroidapp.Model.ErrorResponse;
import com.example.malut.javaandroidapp.Model.GitResponse;
import com.example.malut.javaandroidapp.Model.TrackRecyclerAdapter;
import com.example.malut.javaandroidapp.Model.Track;
import com.example.malut.javaandroidapp.R;
import com.example.malut.javaandroidapp.Model.OnTrackInfoPass;
import com.example.malut.javaandroidapp.Services.ApiCallback;
import com.example.malut.javaandroidapp.Services.RestClient;

import java.util.ArrayList;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrackRecyclerAdapter trackRecyclerAdapter;
    private OnTrackInfoPass infoPass;

    private ArrayList<Track> tracks;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

//        loadRepos("halo");
        tracks = new ArrayList<>();
        trackRecyclerAdapter = new TrackRecyclerAdapter(getActivity(), tracks);
//
//        personList = new ArrayList<>();
//
//        personList.add(new Person("Christina", "Girard", 20, R.drawable.woman1));
//        personList.add(new Person("Phylis", "Payne", 46, R.drawable.man1));
//        personList.add(new Person("Maelya", "Durand", 32, R.drawable.woman2));
//        personList.add(new Person("Roberta", "May", 38, R.drawable.woman3));
//
//        trackRecyclerAdapter = new TrackRecyclerAdapter(getActivity(), personList);
//
        trackRecyclerAdapter.setListener(new OnTrackClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                infoPass.onTrackInfoPass(tracks.get(position));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(trackRecyclerAdapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            infoPass = (OnTrackInfoPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    public void loadRepos(String name) {

        RestClient.getsInstance()
                .getService()
                .getSongsRepos(name, "songTerm")
                .enqueue(new ApiCallback<GitResponse>() {
                    @Override
                    public void success(Response<GitResponse> response) {
                        tracks.clear();
                        tracks.addAll(response.body().getTracks());
                        Log.d("Looooog", tracks.get(0).getArtistName());
                        trackRecyclerAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void failure(ErrorResponse gitRepoError) {

                    }
                });


    }




}
