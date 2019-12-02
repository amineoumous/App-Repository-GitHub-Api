package com.neetking.github.trendingrepos.api;

import com.neetking.github.trendingrepos.model.RepositoryResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by neetking on 02/12/2019.
 */

public interface RepositoryApiService {

   @GET("/search/repositories")
   Call<RepositoryResponse> getRepositoryList(@QueryMap(encoded = false)  Map<String,String> filter );



}
