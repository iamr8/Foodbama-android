package com.mikaco.foodbama.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikaco.foodbama.r8.App;
import com.mikaco.foodbama.r8.RetroInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroAdapter {
    Retrofit retroClient;
    RetroInterface retroInterface;

    public RetroAdapter() {
        try {
            Gson gson = new GsonBuilder().setLenient().create();
            this.retroClient = new Builder()
                    .baseUrl("https://www.foodbama.ir/api/v1/")
                    .client(new OkHttpClient().newBuilder()
                            .readTimeout(30, TimeUnit.SECONDS)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        retroInterface = this.retroClient.create(RetroInterface.class);
    }

    public RetroInterface getInterface() {
        return this.retroInterface;
    }
    public Retrofit getClient() {
        return this.retroClient;
    }

}
