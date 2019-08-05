package com.example.malut.javaandroidapp.services;


import com.example.malut.javaandroidapp.model.ITunesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search?limit=100&media=music")
    Observable<ITunesResponse> getSongsRepos(@Query("term") String term,
                                             @Query("attribute") String attribute);
}
