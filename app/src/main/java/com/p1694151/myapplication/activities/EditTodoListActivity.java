package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.p1694151.myapplication.R;

/**
 * Created by paalwinder on 15/02/18.
 */

public class EditTodoListActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonDelete;
    private Button buttonAdd;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_list);

        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonAdd = (Button) findViewById(R.id.button_add);
        tvBack = (TextView) findViewById(R.id.tv_back);

        buttonDelete.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_delete:
                intent = new Intent(this, DailyEventsActivity.class);
                startActivity(intent);
                break;
            case R.id.button_add:
                intent = new Intent(this, DailyEventsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}