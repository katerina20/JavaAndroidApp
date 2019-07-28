package com.example.malut.javaandroidapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ITunesResponse {

    @SerializedName("results")
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }
}
