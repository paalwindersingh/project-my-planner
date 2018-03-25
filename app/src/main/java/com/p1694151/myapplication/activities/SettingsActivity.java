package com.p1694151.myapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.GeneralResponse;
import com.p1694151.myapplication.models.User;
import com.p1694151.myapplication.storage.LocalStore;
import com.p1694151.myapplication.utils.CustomButton;
import com.p1694151.myapplication.webservice.Constants;
import com.p1694151.myapplication.webservice.RestClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paalwinder on 15/02/18.
 */

public class SettingsActivity extends AppCompatActivity {

    private CustomButton buttonSave;
    private TextView tvName;
    private EditText etFName;
    private EditText etLName;
    private EditText etDob;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;
    private RadioGroup radioGroup;
    private String gender="";
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
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        User user = LocalStore.getUser();
        if(user!=null){
            tvName.setText("Hello, "+user.getFirstname()+" "+user.getLastname());
            etFName.setText(user.getFirstname());
            etLName.setText(user.getLastname());
            etDob.setText(user.getDob());
            etEmail.setText(user.getEmail());
            etPassword.setText(user.getPassword());
            etPhone.setText(user.getPhone());
        }

        buttonSave.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                updateProfile();
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etDob.setText(sdf.format(myCalendar.getTime()));
            }

        };

        etDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SettingsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    gender = (String) rb.getText();
                }

            }
        });

    }

    private void updateProfile() {
        User user = new User();
        user.setFirstname(etFName.getText().toString());
        user.setLastname(etLName.getText().toString());
        user.setDob(etDob.getText().toString());
        user.setGender(gender);
        user.setPassword(etPassword.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setEmail(etEmail.getText().toString());

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()){
            Toast.makeText(SettingsActivity.this, "Please enter valid email id.", Toast.LENGTH_SHORT).show();
        }else if(user.getPassword().isEmpty()){
            Toast.makeText(SettingsActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        }else{
            callupdateProfileApi(user);
        }
    }

    private void callupdateProfileApi(final User user) {
        buttonSave.showProgressIndicator(true);
        StringBuilder query = new StringBuilder();
        if(!user.getEmail().isEmpty()){
            query.append("&");
            query.append(user.getEmail());
        }
        if(!user.getFirstname().isEmpty()){
            query.append("&");
            query.append(user.getFirstname());
        }
        if(!user.getLastname().isEmpty()){
            query.append("&");
            query.append(user.getLastname());
        }
        if(!user.getPhone().isEmpty()){
            query.append("&");
            query.append(user.getPhone());
        }
        if(!user.getDob().isEmpty()){
            query.append("&");
            query.append(user.getDob());
        }
        if(!user.getGender().isEmpty()){
            query.append("&");
            query.append(user.getGender());
        }
        if(!user.getPassword().isEmpty()){
            query.append("&");
            query.append(user.getPassword());
        }


        Call<GeneralResponse> call = RestClient.apiService.changeUsername(query.toString());
        call.enqueue(new Callback<GeneralResponse>() {

            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                GeneralResponse signupResponse = response.body();
                if (signupResponse.getStatus().equals(Constants.SUCCESS)) {
                    LocalStore.setUser(user);
                    Toast.makeText(SettingsActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SettingsActivity.this, "Error updating profile. Please try again!", Toast.LENGTH_SHORT).show();
                    buttonSave.showProgressIndicator(false);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, "Error updating profile. Please try again!", Toast.LENGTH_SHORT).show();
                buttonSave.showProgressIndicator(false);
            }

        });

    }

}