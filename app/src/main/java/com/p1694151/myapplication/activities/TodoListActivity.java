package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.utils.CustomButton;

/**
 * Created by paalwinder on 15/02/18.
 */

public class TodoListActivity extends AppCompatActivity{

    private CustomButton buttonCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonCreate = (CustomButton) findViewById(R.id.button_create);

        buttonCreate.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                finish();
            }
        });
    }

}