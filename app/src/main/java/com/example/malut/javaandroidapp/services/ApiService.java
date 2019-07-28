package com.example.malut.javaandroidapp.services;


import com.example.malut.javaandroidapp.model.ITunesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search?limit=100&media=music")
    Call<ITunesResponse> getSongsRepos(@Query("term") String term,
                                       @Query("attribute") String attribute);
}
