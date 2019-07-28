package com.example.malut.javaandroidapp.services;

import com.example.malut.javaandroidapp.model.ErrorResponse;


import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void success(Response<T> response);

    public abstract void failure(ErrorResponse gitRepoError);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful()){
            Converter<ResponseBody, ErrorResponse> converter = RestClient
                    .getsInstance()
                    .getRetrofit()
                    .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
            try {
                ErrorResponse errorResponse = converter.convert(response.errorBody());
                failure(errorResponse);
            } catch (Exception e) {
                failure(new ErrorResponse("Unhandled error! Code: " + response.code()));
            }
        } else {
            success(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        failure(new ErrorResponse("Unexpected error! Info: " + t.getMessage()));
    }
}
