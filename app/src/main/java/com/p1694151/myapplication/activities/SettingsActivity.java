package com.p1694151.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.User;
import com.p1694151.myapplication.storage.LocalStore;
import com.p1694151.myapplication.utils.CustomButton;

/**
 * Created by paalwinder on 15/02/18.
 */

public class SettingsActivity extends AppCompatActivity {

    private CustomButton buttonSave;
    private TextView tvName;
    private EditText etFName;
    private EditText etLName;
    private EditText etDob;
    private EditText etGender;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.tv_name);
        buttonSave = (CustomButton) findViewById(R.id.button_save);
        etFName = (EditText) findViewById(R.id.et_fname);
        etLName = (EditText) findViewById(R.id.et_lname);
        etDob = (EditText) findViewById(R.id.et_dob);
        etGender = (EditText) findViewById(R.id.et_gender);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        User user = LocalStore.getUser();
        if(user!=null){
            tvName.setText("Hello, "+user.getFirstname()+" "+user.getLastname());
            etFName.setText(user.getFirstname());
            etLName.setText(user.getLastname());
            etGender.setText(user.getGender());
            etDob.setText(user.getDob());
            etEmail.setText(user.getEmail());
            etPassword.setText(user.getPassword());
            etPhone.setText(user.getPhone());
        }

        buttonSave.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                //signup();
            }
        });

    }

}