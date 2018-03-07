package com.p1694151.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.User;
import com.p1694151.myapplication.storage.LocalStore;

/**
 * Created by paalwinder on 15/02/18.
 */

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvGender;
    private TextView tvDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvDob = (TextView) findViewById(R.id.tv_dob);
        tvGender = (TextView) findViewById(R.id.tv_gender);

        User user = LocalStore.getUser();
        if (user != null) {
            tvName.setText("Hello, " + user.getFirstname() + " " + user.getLastname());
            tvEmail.setText("Email: " + user.getEmail());
            tvPhone.setText("Phone: " + user.getPhone());
            tvDob.setText("DOB: " + user.getDob());
            tvGender.setText("Gender: " + user.getGender());
        }
    }
}