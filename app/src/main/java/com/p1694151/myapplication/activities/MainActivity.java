package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.p1694151.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignup;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSignup = (Button) findViewById(R.id.button_signup);
        buttonLogin = (Button) findViewById(R.id.button_login);

        buttonSignup.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_signup:
                intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.button_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
