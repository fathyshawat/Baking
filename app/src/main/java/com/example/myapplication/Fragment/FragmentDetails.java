package com.example.myapplication.Fragment;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.VideoAdapter;
import com.example.myapplication.Api.Constant;
import com.example.myapplication.Utils.CallBackData;
import com.example.myapplication.Model.IngredientsItem;
import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.StepsItem;
import com.example.myapplication.R;
import com.example.myapplication.Activties.VideoActivity;
import com.example.myapplication.Widget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDetails extends Fragment implements CallBackData {

    private static final String TAG = FragmentDetails.class.getSimpleName();
    @BindView(R.id.ingredients_list_text_view)
    TextView ingredientsText;
    @BindView(R.id.step_recycler_view)
    RecyclerView stepRecyclerView;
    String steps, ingredients;
    Gson gson;
    VideoAdapter videoAdapter;
    @BindView(R.id.fab_widget)
    FloatingActionButton widgetAddButton;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.back_button)
    ImageButton backButton;
    private List<StepsItem> stepList;
    private List<IngredientsItem> ingredientList;
    private boolean twoPane;
    private Parcelable mListState;

    public FragmentDetails() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        steps = bundle.getString(Constant.KEY_STEPS_JSON);
        ingredients = bundle.getString(Constant.KEY_INGREDIENTS_JSON);
        gson = new Gson();
        ingredientList = gson.fromJson(ingredients,
                new TypeToken<List<IngredientsItem>>() {
                }.getType());
        stepList = gson.fromJson(steps,
                new TypeToken<List<StepsItem>>() {
                }.getType());
        Log.d("stepList", stepList + "");
        Log.d("step", steps + "");

        twoPane = bundle.getBoolean(Constant.KEY_PANE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        StringBuffer stringBuffer = new StringBuffer();
        for (IngredientsItem ingredient : ingredientList) {
            stringBuffer.append("\u2022 " + ingredient.getQuantity() + " " +
                    ingredient.getIngredient() + " " + ingredient.getMeasure() + "\n");
        }
        setHasOptionsMenu(true);
        ingredientsText.setText(stringBuffer.toString());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stepRecyclerView.setLayoutManager(linearLayoutManager);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(Constant.RECYCLER_VIEW_STATE);
        }
        Log.d(TAG, stepList.size() + "");
        videoAdapter = new VideoAdapter(getActivity(), stepList);
        videoAdapter.setOnClick(this);
        stepRecyclerView.setAdapter(videoAdapter);
        widgetAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                Result result = gson.fromJson(sharedPreferences.getString(Constant.WIDGET_RESULT, null), Result.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                Bundle bundle = new Bundle();
                int appWidgetId = bundle.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                Widget.updateAppWidget(getActivity(), appWidgetManager, appWidgetId, result.getName(),
                        result.getIngredients());
                Toast.makeText(getActivity(), "Added " + result.getName() + " to Widget.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().finish();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            linearLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constant.RECYCLER_VIEW_STATE, linearLayoutManager.onSaveInstanceState());
    }

    @Override
    public void onClick(Context context, Integer id, String description, String url, String thumbnailUrl) {
        if (twoPane) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.KEY_STEPS_ID, id);
            bundle.putString(Constant.KEY_STEPS_DESC, description);
            bundle.putString(Constant.KEY_STEPS_URL, url);
            bundle.putBoolean(Constant.KEY_PANE_VID, twoPane);
            bundle.putString(Constant.THUMBNAIL_IMAGE, thumbnailUrl);
            FragmentVideo videoFragment = new FragmentVideo();
            videoFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container_tab, videoFragment).commit();
        } else {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra(Constant.KEY_STEPS_ID, id);
            intent.putExtra(Constant.KEY_STEPS_DESC, description);
            intent.putExtra(Constant.KEY_STEPS_URL, url);
            intent.putExtra(Constant.THUMBNAIL_IMAGE, thumbnailUrl);
            context.startActivity(intent);
        }
    }

}
