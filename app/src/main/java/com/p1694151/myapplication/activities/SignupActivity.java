package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.GeneralResponse;
import com.p1694151.myapplication.models.User;
import com.p1694151.myapplication.storage.LocalStore;
import com.p1694151.myapplication.utils.CustomButton;
import com.p1694151.myapplication.webservice.Constants;
import com.p1694151.myapplication.webservice.RestClient;

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
    private EditText etGender;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonSignup = (CustomButton) findViewById(R.id.button_signup);
        etFName = (EditText) findViewById(R.id.et_fname);
        etLName = (EditText) findViewById(R.id.et_lname);
        etDob = (EditText) findViewById(R.id.et_dob);
        etGender = (EditText) findViewById(R.id.et_gender);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        buttonSignup.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                signup();
            }
        });
    }

    public void signup() {
        User user = new User();
        user.setFirstname(etFName.getText().toString());
        user.setLastname(etLName.getText().toString());
        user.setDob(etDob.getText().toString());
        user.setGender(etGender.getText().toString());
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
        Call<GeneralResponse> call = RestClient.apiService.signup(user.getFirstname(), user.getLastname(), user.getDob(), user.getGender(), user.getEmail(), user.getPassword(), user.getPhone());
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