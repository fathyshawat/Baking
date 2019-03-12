package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.myapplication.Api.Constant;
import com.example.myapplication.Fragment.FragmentDetails;
import com.example.myapplication.Fragment.FragmentVideo;

public class DetalisActivity extends AppCompatActivity {

    private static final String TAG = DetalisActivity.class.getSimpleName();
    private String stepJson, ingredientJson;
    private boolean rotationDetails;
    private boolean twoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState != null) {
            rotationDetails = savedInstanceState.getBoolean(Constant.KEY_ROTATION_DETAIL_ACTIVITY);
        }
        if (findViewById(R.id.video_container_tab) != null) {
            twoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.video_container_tab, new FragmentVideo()).commit();
        }
        if (savedInstanceState == null) {
            //Only initialize when needed for preserving rotations states
            stepJson = getIntent().getStringExtra(Constant.KEY_STEPS);
            ingredientJson = getIntent().getStringExtra(Constant.KEY_INGREDIENTS);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_STEPS_JSON, stepJson);
            bundle.putString(Constant.KEY_INGREDIENTS_JSON, ingredientJson);
            bundle.putBoolean(Constant.KEY_PANE, twoPane);
            Log.d(TAG, "Pane: " + twoPane);
            FragmentDetails detailFragment = new FragmentDetails();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, detailFragment).commit();
            rotationDetails = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getBoolean(Constant.KEY_ROTATION_DETAIL_ACTIVITY, rotationDetails);
    }


}

