package com.example.myapplication.Fragment.MainFragment;

import android.content.Context;

import com.example.myapplication.Model.Result;

import java.util.List;

public class MainPresenter implements MvpMain.Presenter, MvpMain.Listner {

    MvpMain.View view;
    MvpMain.Model model;

    public MainPresenter(MvpMain.View view) {
        this.view = view;
        model = new MainFragmentInteractor(this);
    }

    @Override
    public void connect(Context context) {
        model.getRecipe(context);
    }

    @Override
    public void load() {
        view.showProgress();
    }

    @Override
    public void stop() {
        view.hideProgress();
    }

    @Override
    public void success(List<Result> result) {
        view.showrecipe(result);
    }

    @Override
    public void fail(String message) {
        view.error(message);
    }
}
