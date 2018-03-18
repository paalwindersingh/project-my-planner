package com.p1694151.myapplication.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.p1694151.myapplication.R;

public class EventHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public TextView tvTime;

    public EventHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvTime = itemView.findViewById(R.id.tv_time);
    }

}