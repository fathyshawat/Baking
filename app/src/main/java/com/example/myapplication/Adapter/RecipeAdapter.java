package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Api.Constant;
import com.example.myapplication.DetalisActivity;
import com.example.myapplication.Model.IngredientsItem;
import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.StepsItem;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    Context context;
    List<Result> data;
    List<IngredientsItem> ingredientList;
    List<StepsItem> stepList;
    private Intent intent;
    private Gson gson;
    SharedPreferences sharedPreferences;
    public RecipeAdapter(Context context, List<Result> data) {
        this.context = context;
        this.data = data;
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.dishText.setText(data.get(position).getName());
        String servings = context.getString(R.string.servings) + " " +
                String.valueOf(data.get(position).getServings());
        String imageUrl = data.get(position).getImage();
        if (!imageUrl.equals("")) {
            //Load image if present
            Picasso.with(context).load(imageUrl).into(holder.imageView);
        }
        holder.servingText.setText(servings);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientList = data.get(position).getIngredients();
                stepList = data.get(position).getSteps();
                intent = new Intent(context, DetalisActivity.class);
                gson = new Gson();
                String ingredientJson = gson.toJson(ingredientList);
                Log.d("GsonList",ingredientJson);
                String stepJson = gson.toJson(stepList);
                intent.putExtra(Constant.KEY_INGREDIENTS, ingredientJson);
                intent.putExtra(Constant.KEY_STEPS, stepJson);
                String resultJson = gson.toJson(data.get(position));
                sharedPreferences.edit().putString(Constant.WIDGET_RESULT, resultJson).apply();
                context.startActivity(intent);
            }
        });
        Log.d("data", servings);

    }

    @Override
    public int getItemCount() {

        Log.d("data", data.size() + "");
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_text_view)
        TextView dishText;

        @BindView(R.id.servings_text_view)
        TextView servingText;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.dish_image_view)
        AppCompatImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
