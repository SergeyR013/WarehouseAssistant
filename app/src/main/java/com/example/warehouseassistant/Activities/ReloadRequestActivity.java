package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.OnPiecesLoadedListener;
import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.OrderAdapter;
import com.example.warehouseassistant.DataModel.PieceAdapter;
import com.example.warehouseassistant.DataModel.ReloadRequests;
import com.example.warehouseassistant.DataModel.ReloadRequestsAdapter;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class ReloadRequestActivity extends AppCompatActivity {
    private String idUser;
    private ReloadRequestsAdapter reqAdapter;
    private List<ReloadRequests> requestsList;
    private Boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload_request);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");
        isEdit = intent.getBooleanExtra("isEdit", false);

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerView);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        requestsList = new ArrayList<>();
        reqAdapter = new ReloadRequestsAdapter(requestsList, this, idUser, isEdit);

        recyclerViewOrders.setAdapter(reqAdapter);

        LoadRequests();
    }

    private void LoadRequests(){
        DataBase db = new DataBase();
        db.LoadReloadRequests(requestsList, reqAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FunctionChoiceActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void goBack(View view){
        onBackPressed();
    }
}