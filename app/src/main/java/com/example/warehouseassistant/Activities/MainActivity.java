package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.warehouseassistant.ErrorHandler.ErrorHandler;
import com.example.warehouseassistant.R;
import com.example.warehouseassistant.UserInitialization.UserInitialization;

public class MainActivity extends AppCompatActivity {

    Button enterBtn;
    EditText pwd, lgn;
    UserInitialization uInit;
    ErrorHandler errorHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterBtn = findViewById(R.id.login_button);
        pwd = findViewById(R.id.password);
        lgn = findViewById(R.id.login);

        uInit = new UserInitialization();
    }

    @Override
    public void onBackPressed() {
        // Do nothing to disable the back button
    }

    public void btnClickEnter(View view){
        try{
            String password = pwd.getText().toString(), login = lgn.getText().toString();
            uInit = new UserInitialization();
            uInit.StartInput(login, password, "", "", "", this, FunctionChoiceActivity.class);
            uInit.CheckAccess();

        } catch (Exception ex){
            errorHandler = new ErrorHandler(this, ex);
            errorHandler.HandleError();
        }
    }
}