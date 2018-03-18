package com.p1694151.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.activities.AddEventActivity;
import com.p1694151.myapplication.activities.TodoListActivity;
import com.p1694151.myapplication.models.Event;
import com.p1694151.myapplication.viewholder.EventHolder;

import java.util.ArrayList;

/**
 * Created by paalwinder on 07/03/18.
 */

public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PPF_VIEW = 0;

    private ArrayList<Event> itemList = new ArrayList<>();
    private int width;
    private Context context;

    public EventListAdapter(ArrayList<Event> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PPF_VIEW:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.event_item, parent, false);
                EventHolder todoItemHolder = new EventHolder(view);
                return todoItemHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case PPF_VIEW:
                EventHolder itemListViewHolder = (EventHolder) holder;
                bindInvetmentView(itemList.get(position), itemListViewHolder);
                break;

            default:
                break;
        }
    }

    private void bindInvetmentView(final Event item, EventHolder itemListViewHolder) {
        itemListViewHolder.tvTitle.setText(item.getDescription());
        itemListViewHolder.tvTime.setText(item.getStart_Time()+" - "+item.getEnd_Time());
        itemListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEventActivity.class);
                intent.putExtra("isEditView", true);
                intent.putExtra("event", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

