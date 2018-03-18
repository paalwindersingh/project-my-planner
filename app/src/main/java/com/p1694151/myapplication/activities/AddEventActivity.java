package com.p1694151.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.GeneralResponse;
import com.p1694151.myapplication.utils.CustomButton;
import com.p1694151.myapplication.webservice.Constants;
import com.p1694151.myapplication.webservice.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paalwinder on 15/02/18.
 */

public class AddEventActivity extends AppCompatActivity {

    private CustomButton buttonAddEvent;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etStartDate;
    private EditText etStartTime;
    private EditText etEndDate;
    private EditText etEndTime;
    private EditText etAddress;
    private EditText etCity;
    private EditText etState;
    private EditText etPostCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonAddEvent = (CustomButton) findViewById(R.id.button_create_event);

        buttonAddEvent.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                addEvent();
            }
        });

        etTitle = (EditText) findViewById(R.id.et_title);
        etDescription = (EditText) findViewById(R.id.et_description);
        etStartDate = (EditText) findViewById(R.id.et_start_date);
        etStartTime = (EditText) findViewById(R.id.et_start_time);
        etEndDate = (EditText) findViewById(R.id.et_end_date);
        etEndTime = (EditText) findViewById(R.id.et_end_time);
        etAddress = (EditText) findViewById(R.id.et_address);
        etState = (EditText) findViewById(R.id.et_state);
        etCity = (EditText) findViewById(R.id.et_city);
        etPostCode = (EditText) findViewById(R.id.et_post);

    }

    private void addEvent() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String startTime = etStartTime.getText().toString();
        String endTime = etEndTime.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String address = etAddress.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        String pin = etPostCode.getText().toString();
        String reminderStartTime = "";
        String reminderEndTime = "";
        String reminder = "";

        Call<GeneralResponse> call = RestClient.apiService.addEvent("&" + title+"&"+description+"&"+
                startTime+"&"+endTime+"&"+startDate+"&"+endDate+
                "&"+address+"&"+city+"&"+state+"&"+pin+"&"+reminderStartTime+"&"+reminderEndTime+"&"+reminder);
        call.enqueue(new Callback<GeneralResponse>() {

            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                GeneralResponse res = response.body();
                if (res.getStatus().equals(Constants.SUCCESS)) {
                    Toast.makeText(AddEventActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddEventActivity.this, "Error adding event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(AddEventActivity.this, "Error adding item", Toast.LENGTH_SHORT).show();
            }

        });

    }

}