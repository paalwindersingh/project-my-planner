package com.p1694151.myapplication.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.p1694151.myapplication.R;

public class TodoItemHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public TextView tvNotes;

    public TodoItemHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvNotes = itemView.findViewById(R.id.tv_notes);
    }

}