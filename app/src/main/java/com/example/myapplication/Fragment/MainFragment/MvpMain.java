package com.example.myapplication.Fragment.MainFragment;

import android.content.Context;

import com.example.myapplication.Model.Result;

import java.util.List;

public interface MvpMain {

    interface View {

        void showrecipe(List<Result> result);

        void error(String message);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {

        void connect(Context context);
    }

    interface Model {

        void getRecipe(Context context);

    }
    interface Listner{

        void load();
        void stop();
        void success(List<Result> result);
        void fail(String message);

    }
}
