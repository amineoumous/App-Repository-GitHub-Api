package com.neetking.github.trendingrepos.utils;

/**
 * Created by neetking on 02/12/2019.
 */

public class APIError {
    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
