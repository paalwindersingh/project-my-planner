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

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreate;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        buttonCreate = (Button) findViewById(R.id.button_create);
        tvBack = (TextView) findViewById(R.id.tv_back);

        buttonCreate.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_create:
                intent = new Intent(this, AddEventActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}