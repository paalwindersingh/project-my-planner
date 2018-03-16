package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.storage.LocalStore;

/**
 * Created by paalwinder on 05/03/18.
 */

public class SplashActivity extends AppCompatActivity {

    private Button buttonSignup;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(LocalStore.getUser() == null) {
                    Intent intent = new Intent(SplashActivity.this, DrawerActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);
    }

}
