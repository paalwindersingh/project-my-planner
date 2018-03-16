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

public class SignupActivity extends AppCompatActivity {

    private CustomButton buttonSignup;
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
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonSignup = (CustomButton) findViewById(R.id.button_signup);
        etFName = (EditText) findViewById(R.id.et_fname);
        etLName = (EditText) findViewById(R.id.et_lname);
        etDob = (EditText) findViewById(R.id.et_dob);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        buttonSignup.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                signup();
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
                new DatePickerDialog(SignupActivity.this, date, myCalendar
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

    public void signup() {
        User user = new User();
        user.setFirstname(etFName.getText().toString());
        user.setLastname(etLName.getText().toString());
        user.setDob(etDob.getText().toString());
        user.setGender(gender);
        user.setPassword(etPassword.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setEmail(etEmail.getText().toString());

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()){
            Toast.makeText(SignupActivity.this, "Please enter valid email id.", Toast.LENGTH_SHORT).show();
        }else if(user.getPassword().isEmpty()){
            Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        }else{
            callSignupApi(user);
        }
    }

    private void callSignupApi(final User user) {
        buttonSignup.showProgressIndicator(true);
        StringBuilder query = new StringBuilder();
        if(!user.getFirstname().isEmpty()){
            query.append("&");
            query.append(user.getFirstname());
        }
        if(!user.getLastname().isEmpty()){
            query.append("&");
            query.append(user.getLastname());
        }
        if(!user.getDob().isEmpty()){
            query.append("&");
            query.append(user.getDob());
        }
        if(!user.getGender().isEmpty()){
            query.append("&");
            query.append(user.getGender());
        }
        if(!user.getEmail().isEmpty()){
            query.append("&");
            query.append(user.getEmail());
        }
        if(!user.getPassword().isEmpty()){
            query.append("&");
            query.append(user.getPassword());
        }
        if(!user.getPhone().isEmpty()){
            query.append("&");
            query.append(user.getPhone());
        }

        Call<GeneralResponse> call = RestClient.apiService.signup(query.toString());
        call.enqueue(new Callback<GeneralResponse>() {

            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                GeneralResponse signupResponse = response.body();
                if (signupResponse.getStatus().equals(Constants.SUCCESS)) {
                    LocalStore.setUser(user);
                    Intent intent = new Intent(SignupActivity.this, DrawerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Error signing up. Please try again!", Toast.LENGTH_SHORT).show();
                    buttonSignup.showProgressIndicator(false);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error signing up. Please try again!", Toast.LENGTH_SHORT).show();
                buttonSignup.showProgressIndicator(false);
            }

        });
    }
}
