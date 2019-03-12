package com.example.myapplication.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {

    public static final String BASE_URL = "http://d17h27t6h515a5.cloudfront.net/";

    public static ApiInterface getRecipe(){

        return Constant.getRetrofit(BASE_URL).create(ApiInterface.class);
    }


}