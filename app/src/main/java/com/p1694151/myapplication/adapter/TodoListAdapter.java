package com.p1694151.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.TodoItem;
import com.p1694151.myapplication.viewholder.TodoItemHolder;

import java.util.ArrayList;

/**
 * Created by paalwinder on 07/03/18.
 */

public class TodoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PPF_VIEW = 0;

    private ArrayList<TodoItem> itemList = new ArrayList<>();
    private int width;

    public TodoListAdapter(ArrayList<TodoItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PPF_VIEW:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.todo_item, parent, false);
                TodoItemHolder todoItemHolder = new TodoItemHolder(view);
                return todoItemHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case PPF_VIEW:
                TodoItemHolder itemListViewHolder = (TodoItemHolder) holder;
                bindInvetmentView(itemList.get(position), itemListViewHolder);
                break;

            default:
                break;
        }
    }

    private void bindInvetmentView(final TodoItem item, TodoItemHolder itemListViewHolder) {
        itemListViewHolder.tvTitle.setText(item.getTitle());
        itemListViewHolder.tvNotes.setText(item.getNotes());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

