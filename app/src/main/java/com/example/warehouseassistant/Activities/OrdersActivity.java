package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.OrderAdapter;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerView);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList, this, idUser);

        recyclerViewOrders.setAdapter(orderAdapter);

        LoadOrders();

    }

    private void LoadOrders(){
        DataBase db = new DataBase();
        db.LoadOrders(orderList, orderAdapter);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, FunctionChoiceActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FunctionChoiceActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }


}