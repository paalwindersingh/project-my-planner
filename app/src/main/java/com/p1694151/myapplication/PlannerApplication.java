package com.p1694151.myapplication;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.p1694151.myapplication.storage.LocalStore;

/**
 * Created by paalwinder on 06/03/18.
 */

public class PlannerApplication extends Application{

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "PlannerSharedPreferences";
    private static SharedPreferences mSharedPreferences;
    public LocalStore mLocalStore;

    @Override
    public void onCreate() {
        super.onCreate();

        //initialise shared prefernces
        mSharedPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mLocalStore = new LocalStore(mSharedPreferences, new Gson());

    }
}
