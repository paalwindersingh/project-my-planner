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

public class LoginActivity extends AppCompatActivity {

    private CustomButton buttonLogin;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonLogin = (CustomButton) findViewById(R.id.button_login);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        buttonLogin.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                login();
            }
        });
    }

    private void login() {
        User user = new User();
        user.setPassword(etPassword.getText().toString());
        user.setEmail(etEmail.getText().toString());

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            Toast.makeText(LoginActivity.this, "Please enter valid email id.", Toast.LENGTH_SHORT).show();
        } else if (user.getPassword().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            callLoginApi(user);
        }
    }

    private void callLoginApi(User user) {
        buttonLogin.showProgressIndicator(true);
        StringBuilder query = new StringBuilder();
        if(!user.getEmail().isEmpty()){
            query.append("&");
            query.append(user.getEmail());
        }
        if(!user.getPassword().isEmpty()){
            query.append("&");
            query.append(user.getPassword());
        }
        Call<User> call = RestClient.apiService.signin(query.toString());
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user.getStatus().equals(Constants.SUCCESS)) {
                    LocalStore.setUser(user);
                    Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                    buttonLogin.showProgressIndicator(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error logging in. Please try again!", Toast.LENGTH_SHORT).show();
                buttonLogin.showProgressIndicator(false);
            }

        });
    }

}