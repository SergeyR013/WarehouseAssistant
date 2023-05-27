package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.User;
import com.example.warehouseassistant.R;
import com.example.warehouseassistant.UserInitialization.Input;

import java.security.NoSuchAlgorithmException;

public class userInserting extends AppCompatActivity {

    EditText login;
    EditText password;
    EditText fullName;
    EditText department;
    Spinner role;
    String chosenRole;
    Button insertBtn;
    private Boolean isInsert;
    private String idUser;
    private User user;



    String[] countries = { "Бригадир", "Админ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inserting);

        Intent intent = getIntent();
        isInsert = intent.getBooleanExtra("isInsert", false);
        idUser = intent.getStringExtra("idUser");
        user = (User) intent.getSerializableExtra("User");

        login = findViewById(R.id.loginInsert);
        password = findViewById(R.id.passwordInsert);
        insertBtn = findViewById(R.id.insert_button);

        if (!isInsert) insertBtn.setText("Редактировать");

        role = findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.item_spinner, R.id.text_role, countries);
        // Применяем адаптер к элементу spinner
        role.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                if (item.equals("Бригадир")){
                    chosenRole = "2";
                } else{
                    chosenRole = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        role.setOnItemSelectedListener(itemSelectedListener);



        fullName = findViewById(R.id.fullName);
        department = findViewById(R.id.department);

        if (!isInsert){
            if (user != null){
                int position = adapter.getPosition(user.getUser_group());
                if (position != -1){
                    role.setSelection(position);
                }
            }
            fullName.setText(user.getFullName());
            login.setText(user.getDomain_name());
            department.setText(user.getDepartment());
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, adminPanel.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void insertUser(View view) throws NoSuchAlgorithmException {
        Input input = new Input();
        String pwd = password.getText().toString(), lgn = login.getText().toString(), userRole = chosenRole,
                FIO = fullName.getText().toString(), dep = department.getText().toString();
        if (isInsert){
            DataBase db = new DataBase();
            db.InsertUser(pwd, lgn, userRole, FIO, dep, this, idUser);
        } else{
            if (!pwd.equals("")){
                input.setPassword(pwd);
                pwd = input.getPassword();
                DataBase db = new DataBase();
                db.EditUser(pwd, lgn, userRole, FIO, dep, user.getId(), idUser, this);
            } else {
                Toast.makeText(this, "Введите пароль!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goBack(View view){
        onBackPressed();
    }


}