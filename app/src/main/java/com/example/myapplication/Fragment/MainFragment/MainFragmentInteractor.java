package com.example.myapplication.Fragment.MainFragment;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.Api.ApiInterface;
import com.example.myapplication.Api.Common;
import com.example.myapplication.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragmentInteractor implements MvpMain.Model {


    MvpMain.Listner listner;

    public MainFragmentInteractor(MvpMain.Listner listner) {
        this.listner = listner;
    }

    @Override
    public void getRecipe(Context context) {

        ApiInterface apiInterface= Common.getRecipe();
        Call<List<Result>> call =apiInterface.getDetails();
        listner.load();
        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {

                if (response.isSuccessful())
                    listner.success(response.body());

                Log.d("retrofit",response.code()+"");

                listner.stop();
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.d("retrofit",t.getMessage()+"");

                listner.stop();
                listner.fail("Check Connection");
            }
        });
    }
}
