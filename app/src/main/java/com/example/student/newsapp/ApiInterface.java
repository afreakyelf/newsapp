package com.example.student.newsapp;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface ApiInterface {

    @GET("{login}")
    Call<ResponseBody> getresponse(@Path("login") String postfix, @QueryMap Map<String, Object> options, @Header("authorization") String auth);

}
