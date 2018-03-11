package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.adapter.TodoListAdapter;
import com.p1694151.myapplication.models.TodoItem;
import com.p1694151.myapplication.models.TodoListResponse;
import com.p1694151.myapplication.models.User;
import com.p1694151.myapplication.storage.LocalStore;
import com.p1694151.myapplication.webservice.Constants;
import com.p1694151.myapplication.webservice.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CalendarView simpleCalendarView;
    private FloatingActionButton addTodo;
    private CardView emptyView;
    private CardView cvHeader;
    private RecyclerView todoListRv;
    private RecyclerView.LayoutManager layoutManager;
    private TodoListAdapter adapter;
    private ArrayList<TodoItem> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        emptyView = (CardView) findViewById(R.id.cardView);
        cvHeader = (CardView) findViewById(R.id.cv_header);
        todoListRv = (RecyclerView) findViewById(R.id.rv_todo_list);
        addTodo = (FloatingActionButton) findViewById(R.id.fab_todo);


        simpleCalendarView = (CalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(DrawerActivity.this, DailyEventsActivity.class);
                startActivity(intent);
            }
        });
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });

        getTodoList();

        setAdapter();
    }

    private void getTodoList() {
        /*//todo replace with data from api call
        todoList.add(new TodoItem(0, "Go to gym", "try exercising for 30mins"));
        todoList.add(new TodoItem(0, "get some grocery", "stop at bigmart while returning from work"));
        todoList.add(new TodoItem(0, "Do assignment", "Work 2hrs daily on final project"));
        todoList.add(new TodoItem(0, "Submit update", "submit update twice a week"));*/

        Call<TodoListResponse> call = RestClient.apiService.getTodoList("&"+LocalStore.getUser().getUserid());
        call.enqueue(new Callback<TodoListResponse>() {

            @Override
            public void onResponse(Call<TodoListResponse> call, Response<TodoListResponse> response) {
                todoList.clear();
                TodoListResponse res = response.body();
                if (res.getStatus().equals(Constants.SUCCESS)) {
                    todoList.addAll(res.getTodoList());
                    emptyView.setVisibility(todoList.isEmpty() ? View.VISIBLE : View.GONE);
                    cvHeader.setVisibility(!todoList.isEmpty() ? View.VISIBLE : View.GONE);
                } else {
                    /*emptyView.setVisibility(todoList.isEmpty() ? View.VISIBLE : View.GONE);
                    cvHeader.setVisibility(!todoList.isEmpty() ? View.VISIBLE : View.GONE);*/
                }
            }

            @Override
            public void onFailure(Call<TodoListResponse> call, Throwable t) {
            }

        });
    }

    private void setAdapter() {
        adapter = new TodoListAdapter(todoList);
        layoutManager = new LinearLayoutManager(this);

        todoListRv.setLayoutManager(layoutManager);
        todoListRv.setItemAnimator(new DefaultItemAnimator());
        todoListRv.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            LocalStore.clearUser();
            Intent loginscreen=new Intent(this,MainActivity.class);
            loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginscreen);
            finish();
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_delete) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
