package com.neetking.github.trendingrepos.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by neetking on 02/12/2019.
 */

public class RepositoryApiMaker {

    public static final String BASE_URL = "https://api.github.com";

    private Retrofit retrofit;

    public RepositoryApiMaker() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RepositoryApiService getService() {

        return retrofit.create(RepositoryApiService.class);
    }
}
