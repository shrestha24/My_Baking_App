package com.example.my_baking_app.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequests {
    private static final String BASE_URL="https://d17h27t6h515a5.cloudfront.net";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            retrofit= new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
