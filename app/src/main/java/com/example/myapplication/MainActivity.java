package com.example.myapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Fragment.MainFragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

private static boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.tab_list_recipe_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

    }



    public static boolean getNoPane() {
        return mTwoPane;
    }


}
