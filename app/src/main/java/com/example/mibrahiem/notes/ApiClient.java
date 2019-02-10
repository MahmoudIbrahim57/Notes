package com.example.mibrahiem.notes;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String base_URL="http://my-notes-retrofit.000webhostapp.com/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if(retrofit==null)
        {


            retrofit = new Retrofit.Builder()
                    .baseUrl(base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit;
    }


}