package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.p1694151.myapplication.R;
import com.p1694151.myapplication.adapter.TodoListAdapter;
import com.p1694151.myapplication.models.Event;
import com.p1694151.myapplication.models.EventListResponse;
import com.p1694151.myapplication.models.TodoItem;
import com.p1694151.myapplication.models.TodoListResponse;
import com.p1694151.myapplication.models.User;
import com.p1694151.myapplication.storage.LocalStore;
import com.p1694151.myapplication.utils.DateUtils;
import com.p1694151.myapplication.utils.EventDecorator;
import com.p1694151.myapplication.webservice.Constants;
import com.p1694151.myapplication.webservice.RestClient;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private TextView tvName;
    private TextView tvEmail;
    private MaterialCalendarView simpleCalendarView;
    private WeekView weekView;
    private FloatingActionButton addTodo;
    private CardView emptyView;
    private CardView cvHeader;
    private RecyclerView todoListRv;
    private RecyclerView.LayoutManager layoutManager;
    private TodoListAdapter adapter;
    private ArrayList<TodoItem> todoList = new ArrayList<>();
    private ArrayList<Event> eventList = new ArrayList<>();
    List<CalendarDay> events = new ArrayList<>();

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int weekViewType = TYPE_THREE_DAY_VIEW;

    @Override
    protected void onResume() {
        super.onResume();
        if (simpleCalendarView != null) {
            getEventList();
        }
    }

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

        View hView = navigationView.inflateHeaderView(R.layout.nav_header_drawer);

        tvName = (TextView) hView.findViewById(R.id.tv_name);
        tvEmail = (TextView) hView.findViewById(R.id.tv_email);

        User user = LocalStore.getUser();
        /*User user = new User();
        user.setFirstname("Jas");
        user.setEmail("jas@gmail.com");*/
        if (user != null) {
            tvName.setText(user.getFirstname());
            tvEmail.setText(user.getEmail());
        }
        emptyView = (CardView) findViewById(R.id.cardView);
        cvHeader = (CardView) findViewById(R.id.cv_header);
        todoListRv = (RecyclerView) findViewById(R.id.rv_todo_list);
        addTodo = (FloatingActionButton) findViewById(R.id.fab_todo);


        weekView = (WeekView) findViewById(R.id.weekView);
        simpleCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent(DrawerActivity.this, DailyEventsActivity.class);
                intent.putExtra("cal", date.getCalendar().getTimeInMillis());
                intent.putExtra("events", eventList);
                startActivity(intent);
            }
        });

        simpleCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        getEventList();

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, TodoListActivity.class);
                intent.putExtra("isEditView", false);
                startActivity(intent);
            }
        });

        getTodoList();

        setAdapter();

        weeklyCalendarSetup();

        simpleCalendarView.setVisibility(View.GONE);
    }

    private void getEventList() {
        eventList.clear();
        events.clear();

        //todo comment when connected to server
       /* Event event = new Event();
        event.setStart_Date("2018-03-18 00:00:00.0");
        event.setEnd_Date("2018-03-18 00:00:00.0");
        event.setStart_Time("07:00AM");
        event.setEnd_Time("08:00PM");
        event.setDescription("abc 1");
        eventList.add(event);
        event = new Event();
        event.setStart_Date("2018-04-12 00:00:00.0");
        event.setEnd_Date("2018-04-12 00:00:00.0");
        event.setStart_Time("02:00AM");
        event.setEnd_Time("03:00AM");
        event.setDescription("abc 2");
        eventList.add(event);
        event = new Event();
        event.setStart_Date("2018-04-18 00:00:00.0");
        event.setEnd_Date("2018-04-18 00:00:00.0");
        event.setStart_Time("07:00AM");
        event.setEnd_Time("08:00PM");
        event.setDescription("abc 3");
        eventList.add(event);
        event = new Event();
        event.setStart_Date("2018-04-13 00:00:00.0");
        event.setEnd_Date("2018-04-13 00:00:00.0");
        event.setStart_Time("07:00AM");
        event.setEnd_Time("08:00PM");
        event.setDescription("abc 4");
        eventList.add(event);
        event = new Event();
        event.setStart_Date("2018-05-10 00:00:00.0");
        event.setEnd_Date("2018-05-10 00:00:00.0");
        event.setStart_Time("07:00AM");
        event.setEnd_Time("08:00PM");
        event.setDescription("abc 5");
        eventList.add(event);
        event = new Event();
        event.setStart_Date("2018-03-18 00:00:00.0");
        event.setEnd_Date("2018-03-18 00:00:00.0");
        event.setStart_Time("02:00AM");
        event.setEnd_Time("08:00PM");
        event.setDescription("abc 6");
        eventList.add(event);
        for (int i = 0; i < eventList.size(); i++) {
            Event event1 = eventList.get(i);
            CalendarDay eventDay = CalendarDay.from(DateUtils.getCalenderInstance(event1.getStart_Date(), DateUtils.TIMESTAMP_FORMAT));
            events.add(eventDay);
        }
        simpleCalendarView.addDecorator(new EventDecorator(Color.RED, events));*/
        Call<EventListResponse> call = RestClient.apiService.getEventList("");
        call.enqueue(new Callback<EventListResponse>() {

            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                todoList.clear();
                EventListResponse res = response.body();

                if (res.getStatus().equals(Constants.SUCCESS)) {
                    eventList.addAll(res.getEventList());
                    for (int i = 0; i < eventList.size(); i++) {
                        Event event1 = eventList.get(i);
                        CalendarDay eventDay = CalendarDay.from(DateUtils.getCalenderInstance(event1.getStart_Date(), DateUtils.TIMESTAMP_FORMAT));
                        events.add(eventDay);
                    }
                    simpleCalendarView.addDecorator(new EventDecorator(Color.RED, events));
                } else {

                }
            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
            }

        });
    }

    private void weeklyCalendarSetup() {
        // Show a toast message about the touched event.
        weekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        weekView.setMonthChangeListener(this);

        // Set long press listener for events.
        weekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        weekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Intent intent = new Intent(DrawerActivity.this, DailyEventsActivity.class);
        intent.putExtra("events", eventList);
        intent.putExtra("cal", event.getStartTime().getTimeInMillis());
        startActivity(intent);
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Intent intent = new Intent(DrawerActivity.this, DailyEventsActivity.class);
        intent.putExtra("events", eventList);
        intent.putExtra("cal", event.getStartTime().getTimeInMillis());
        startActivity(intent);
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Intent intent = new Intent(DrawerActivity.this, DailyEventsActivity.class);
        intent.putExtra("events", eventList);
        intent.putExtra("cal", time.getTimeInMillis());
        startActivity(intent);
    }


    private void getTodoList() {
        //todo replace with data from api call
       /* todoList.add(new TodoItem(0, "Go to gym", "try exercising for 30mins"));
        todoList.add(new TodoItem(0, "get some grocery", "stop at bigmart while returning from work"));
        todoList.add(new TodoItem(0, "Do assignment", "Work 2hrs daily on final project"));
        todoList.add(new TodoItem(0, "Submit update", "submit update twice a week"));*/

        //Call<TodoListResponse> call = RestClient.apiService.getTodoList("&"+LocalStore.getUser().getUserid());
        Call<TodoListResponse> call = RestClient.apiService.getTodoList("&1");
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
                    emptyView.setVisibility(todoList.isEmpty() ? View.VISIBLE : View.GONE);
                    cvHeader.setVisibility(!todoList.isEmpty() ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TodoListResponse> call, Throwable t) {
            }

        });
    }

    private void setAdapter() {
        adapter = new TodoListAdapter(todoList, this);
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
            Intent loginscreen = new Intent(this, MainActivity.class);
            loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginscreen);
            finish();
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_delete) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        boolean isMonthView = false;
        switch (id) {
            case R.id.action_today:
                weekView.goToToday();
                weekView.setVisibility(View.VISIBLE);
                simpleCalendarView.setVisibility(View.GONE);
                return true;
            case R.id.action_day_view:
                if (weekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    weekViewType = TYPE_DAY_VIEW;
                    weekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    weekView.setVisibility(View.VISIBLE);
                    simpleCalendarView.setVisibility(View.GONE);
                }
                return true;
            case R.id.action_three_day_view:
                if (weekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    weekViewType = TYPE_THREE_DAY_VIEW;
                    weekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    weekView.setVisibility(View.VISIBLE);
                    simpleCalendarView.setVisibility(View.GONE);
                }
                return true;
            case R.id.action_week_view:
                if (weekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    weekViewType = TYPE_WEEK_VIEW;
                    weekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    weekView.setVisibility(View.VISIBLE);
                    simpleCalendarView.setVisibility(View.GONE);
                }
                return true;
            case R.id.action_month_view:
                isMonthView = true;
                weekView.setVisibility(View.GONE);
                simpleCalendarView.setVisibility(View.VISIBLE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        for (int i = 0; i < eventList.size(); i++) {
            Event thisevent = eventList.get(i);


            Calendar startTime = DateUtils.getCalenderInstance(thisevent.getStart_Date(), DateUtils.TIMESTAMP_FORMAT);
            Calendar cal = DateUtils.getCalenderInstance(thisevent.getStart_Time(), DateUtils.TIME_FORMAT);
            int month = startTime.get(Calendar.MONTH);
            if (month == newMonth - 1) {
                int hr = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                startTime.set(Calendar.HOUR_OF_DAY, hr);
                startTime.set(Calendar.MINUTE, min);

                Calendar endTime = (Calendar) startTime.clone();
                cal = DateUtils.getCalenderInstance(thisevent.getEnd_Time(), DateUtils.TIME_FORMAT);
                hr = cal.get(Calendar.HOUR_OF_DAY);
                min = cal.get(Calendar.MINUTE);
                endTime.set(Calendar.HOUR_OF_DAY, hr);
                endTime.set(Calendar.MINUTE, min);

                WeekViewEvent event = new WeekViewEvent(i, thisevent.getDescription(), startTime, endTime);
                events.add(event);
            }
        }
        return events;
    }
}
