package com.neetking.github.trendingrepos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neetking on 02/12/2019.
 */

public class RepositoryResponse {

    @SerializedName("items")
    private List<Repository> items;
    public List<Repository> getItems() {
        return items;
    }

}
