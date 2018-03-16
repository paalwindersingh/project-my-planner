package com.p1694151.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.activities.DrawerActivity;
import com.p1694151.myapplication.activities.TodoListActivity;
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
    private Context context;

    public TodoListAdapter(ArrayList<TodoItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
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
        itemListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TodoListActivity.class);
                intent.putExtra("isEditView", true);
                intent.putExtra("todo", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

