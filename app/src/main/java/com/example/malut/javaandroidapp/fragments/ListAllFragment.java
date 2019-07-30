package com.example.malut.javaandroidapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.malut.javaandroidapp.R;
import com.example.malut.javaandroidapp.adapters.TrackRecyclerAdapter;
import com.example.malut.javaandroidapp.listeners.OnTrackClickListener;
import com.example.malut.javaandroidapp.model.OnTrackInfoPass;
import com.example.malut.javaandroidapp.model.Track;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAllFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrackRecyclerAdapter trackRecyclerAdapter;
    private OnTrackInfoPass infoPass;

    private ArrayList<Track> tracks;

    public ListAllFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_all, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

        if (getArguments() != null) {
            tracks = getArguments().getParcelableArrayList("startList");
        } else {
            tracks = new ArrayList<>();
        }

        trackRecyclerAdapter = new TrackRecyclerAdapter(getActivity(), tracks);

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

    public void fillListWithResult(List<Track> tracks) {
        this.tracks.clear();

        this.tracks.addAll(tracks);
        trackRecyclerAdapter.notifyDataSetChanged();
    }

    public static ListAllFragment newInstance(ArrayList<Track> arrayList) {
        ListAllFragment myFragment = new ListAllFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("startList", arrayList);
        myFragment.setArguments(args);

        return myFragment;
    }
}
