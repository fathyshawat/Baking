package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Utils.CallBackData;
import com.example.myapplication.Model.StepsItem;
import com.example.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    List<StepsItem> data;
    Context context;
    CallBackData callBackData;

    public VideoAdapter(Context context, List<StepsItem> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String shortDescription = data.get(position).getShortDescription();
        holder.stepTextView.setText(shortDescription);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackData.onClick(context, data.get(position).getId(),
                        data.get(position).getDescription(),
                        data.get(position).getVideoURL(),
                        data.get(position).getThumbnailURL());
            }
        });
    }

    public void setOnClick(CallBackData clickCallBack) {
        this.callBackData = clickCallBack;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_name_text_view)
        TextView stepTextView;
        @BindView(R.id.card_video_list)
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
