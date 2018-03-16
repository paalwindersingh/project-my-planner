package com.p1694151.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.p1694151.myapplication.R;
import com.p1694151.myapplication.models.GeneralResponse;
import com.p1694151.myapplication.models.TodoItem;
import com.p1694151.myapplication.models.TodoListResponse;
import com.p1694151.myapplication.utils.CustomButton;
import com.p1694151.myapplication.webservice.Constants;
import com.p1694151.myapplication.webservice.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paalwinder on 15/02/18.
 */

public class TodoListActivity extends AppCompatActivity {

    private CustomButton buttonCreate;
    private CustomButton buttonDelete;
    private TodoItem todoItem;

    private boolean isEditView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isEditView = getIntent().getBooleanExtra("isEditView", false);
        if (isEditView) {
            todoItem = getIntent().getParcelableExtra("todo");
        }

        buttonCreate = (CustomButton) findViewById(R.id.button_create);
        buttonDelete = (CustomButton) findViewById(R.id.button_delete);
        buttonCreate.setText(isEditView ? "Update" : "Create");

        buttonCreate.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                finish();
            }
        });

        buttonDelete.setVisibility(isEditView ? View.VISIBLE : View.GONE);
        buttonDelete.setOnItemClickListener(new CustomButton.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                deleteTodoItem();
            }
        });
    }

    private void deleteTodoItem() {
        Call<GeneralResponse> call = RestClient.apiService.deleteTodoListItem("&" + todoItem.getTask_id());
        call.enqueue(new Callback<GeneralResponse>() {

            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                GeneralResponse res = response.body();
                if (res.getStatus().equals(Constants.SUCCESS)) {
                    Toast.makeText(TodoListActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TodoListActivity.this, "Error deleting item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(TodoListActivity.this, "Error deleting item", Toast.LENGTH_SHORT).show();
            }

        });
    }

}