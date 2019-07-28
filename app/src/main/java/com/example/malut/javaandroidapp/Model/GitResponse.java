package com.example.malut.javaandroidapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitResponse {

    @SerializedName("results")
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }
}
