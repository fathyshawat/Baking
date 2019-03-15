package com.example.myapplication.Activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.myapplication.Api.Constant;
import com.example.myapplication.Fragment.FragmentVideo;
import com.example.myapplication.R;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();
    private Bundle bundle;
    private boolean fragmentCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }
        if (savedInstanceState != null) {
            fragmentCreated = savedInstanceState.getBoolean(Constant.KEY_ROTATION_VIDEO_ACTIVITY);
        }
        if (!fragmentCreated) {

            bundle = new Bundle();
            bundle = getIntent().getExtras();
            FragmentVideo videoFragment = new FragmentVideo();
            videoFragment.setArguments(bundle);
            fragmentCreated = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_fragment, videoFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constant.KEY_ROTATION_VIDEO_ACTIVITY, fragmentCreated);
    }
}
