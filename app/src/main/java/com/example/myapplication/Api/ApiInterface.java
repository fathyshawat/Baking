package com.example.myapplication.Api;

import com.example.myapplication.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Result>> getDetails();
}
