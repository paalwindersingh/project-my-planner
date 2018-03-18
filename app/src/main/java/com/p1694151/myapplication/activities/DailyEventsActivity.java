package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.adapter.EventListAdapter;
import com.p1694151.myapplication.models.Event;
import com.p1694151.myapplication.utils.CustomButton;
import com.p1694151.myapplication.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by paalwinder on 15/02/18.
 */

public class DailyEventsActivity extends AppCompatActivity {

    private CustomButton buttonAddEvent;
    private ArrayList<Event> allEventList = new ArrayList<>();
    private ArrayList<Event> eventList = new ArrayList<>();

    private CardView emptyView;
    private RecyclerView eventListRv;
    private RecyclerView.LayoutManager layoutManager;
    private EventListAdapter adapter;
    private long calTime;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonAddEvent = (CustomButton) findViewById(R.id.button_add);
        emptyView = (CardView) findViewById(R.id.cardView);
        eventListRv = (RecyclerView) findViewById(R.id.rv_event_list);

        buttonAddEvent.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(DailyEventsActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        calTime = getIntent().getLongExtra("cal",0);
        cal = Calendar.getInstance();
        cal.setTimeInMillis(calTime);

        getEventList();

        setAdapter();

    }

    private void getEventList() {
        eventList.clear();
        allEventList = getIntent().getParcelableArrayListExtra("events");
        for (int i = 0; i < allEventList.size(); i++) {
            Event thisEvent = allEventList.get(i);
            Calendar thisCal = DateUtils.getCalenderInstance(thisEvent.getStart_Date(), DateUtils.TIMESTAMP_FORMAT);
            if (thisCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR) &&
                    thisCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                    ){
                eventList.add(thisEvent);
            }

        }
        emptyView.setVisibility(eventList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void setAdapter() {
        adapter = new EventListAdapter(eventList, this);
        layoutManager = new LinearLayoutManager(this);

        eventListRv.setLayoutManager(layoutManager);
        eventListRv.setItemAnimator(new DefaultItemAnimator());
        eventListRv.setAdapter(adapter);
    }

}