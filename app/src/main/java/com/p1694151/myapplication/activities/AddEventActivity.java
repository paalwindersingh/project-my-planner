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

public class AddEventActivity extends AppCompatActivity {

    private CustomButton buttonAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonAddEvent = (CustomButton) findViewById(R.id.button_create_event);

        buttonAddEvent.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                finish();
            }
        });
    }

}