package com.p1694151.myapplication.storage;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.p1694151.myapplication.models.User;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Type;

import static com.p1694151.myapplication.storage.LocalStore.Key.KEY_USER;

public class LocalStore {

    @StringDef({KEY_USER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Key {
        String KEY_USER = "kUser";
    }

    @NonNull
    private static SharedPreferences mSharedPreferences;

    @NonNull
    private static Gson mGson;

    public LocalStore(@NonNull SharedPreferences sharedPreferences, @NonNull Gson gson) {
        this.mSharedPreferences = sharedPreferences;
        this.mGson = gson;
    }

    public static void clearUser() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    //region Generic Setters

    private static void set(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    private static void setBoolean(String key, Boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    private static <T> void set(String key, T obj) {
        String json = mGson.toJson(obj);
        set(key, json);
    }

    private static String get(String key) {
        return mSharedPreferences.getString(key, null);
    }

    private static Boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    private static <T> T get(String key, Class<T> klass) {
        String json = get(key);
        if (json == null) {
            return null;
        }
        try {
            return mGson.fromJson(json, klass);
        } catch (JsonSyntaxException e) {
            Log.w("LocalStoreImpl", "unable to convert json", e);
            return null;
        }
    }

    private static <T> T getList(String key, final Type type) {
        String json = get(key);
        if (json == null) {
            return null;
        }
        try {
            return mGson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            Log.w("LocalStoreImpl", "unable to convert json", e);
            return null;
        }
    }
    //endregion

    public static void setUser(User user) {
        set(KEY_USER, user);
    }

    public static User getUser() {
        return get(KEY_USER, User.class);
    }

}