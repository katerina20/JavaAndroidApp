package com.example.malut.javaandroidapp.services;

import com.example.malut.javaandroidapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient sInstance = new RestClient();

    private ApiService service;
    private Retrofit retrofit;

    private final static String API_URL = "https://itunes.apple.com";

    private RestClient() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDederializer())
                .create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(API_URL)
                .build();

        service = retrofit.create(ApiService.class);
    }

    public static RestClient getsInstance() {
        return sInstance;
    }

    public ApiService getService() {
        return service;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
