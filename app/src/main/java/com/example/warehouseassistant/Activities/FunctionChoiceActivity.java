package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseassistant.R;

public class FunctionChoiceActivity extends AppCompatActivity {
    String idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_choice);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void goOrders(View view){
        Intent intent = new Intent(this, OrdersActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void goReloadRequests(View view){
        Intent intent = new Intent(this, ReloadRequestActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
}