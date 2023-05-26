package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.User;
import com.example.warehouseassistant.DataModel.UserAdapter;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteUserActivity extends AppCompatActivity {

    private String idUser;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");

        RecyclerView recyclerViewUser = findViewById(R.id.recyclerView);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, this, idUser, false);

        recyclerViewUser.setAdapter(userAdapter);

        LoadUsers();
    }

    private void LoadUsers(){
        DataBase db = new DataBase();
        db.GetUser(userList, userAdapter);
    }

    public void goBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, adminPanel.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
}