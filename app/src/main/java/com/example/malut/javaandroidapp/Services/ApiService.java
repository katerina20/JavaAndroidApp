package com.example.malut.javaandroidapp.Services;


import com.example.malut.javaandroidapp.Model.GitResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search?limit=20&media=music")
    Call<GitResponse> getSongsRepos(@Query("term") String term,
                                   @Query("attribute") String attribute);
}
