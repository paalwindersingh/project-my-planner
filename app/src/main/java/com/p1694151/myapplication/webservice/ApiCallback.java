package com.p1694151.myapplication.webservice;

import android.accounts.NetworkErrorException;
import android.content.res.Resources;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paalwinder on 23/06/17.
 */

public abstract class ApiCallback<T> implements Callback<T> {

    private final String INVALID_SKEY = "SKEY token is missing or invalid";
    private final String TIMEOUT_SKEY = "You must log in via /user/login";

    public abstract void handleSuccess(T obj);

    public abstract void handleFailure();

    private OnErrorListener onErrorListener;

    public ApiCallback(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            handleSuccess(response.body());
        } else {
            onFailure(call, new Resources.NotFoundException());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            if (onErrorListener != null) {
                onErrorListener.onThrowUnknownHostOrConnectEvent();
            }
        } else if (t instanceof IOException) {
            //timeout error
        } else if (t instanceof IllegalStateException) {

        } else if (t instanceof NetworkErrorException) {

        } else if (t instanceof ParseException) {

        }
        if (!call.isCanceled()) {
            handleFailure();
        }
    }

    public interface OnErrorListener {
        void onThrowSKeyInvalidEvent();

        void onThrowUnknownHostOrConnectEvent();
    }
}