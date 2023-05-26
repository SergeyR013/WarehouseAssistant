package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.warehouseassistant.R;

public class adminPanel extends AppCompatActivity {
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void insertUser(View view){
        Intent intent = new Intent(this, userInserting.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("isInsert", true);
        startActivity(intent);
    }

    public void deleteUser(View view){
        Intent intent = new Intent(this, DeleteUserActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void editUser(View view){
        Intent intent = new Intent(this, editUserActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("isInsert", false);
        startActivity(intent);
    }

    public void goBack(View view){
        onBackPressed();
    }
}