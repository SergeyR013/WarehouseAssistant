package com.example.warehouseassistant.Activities;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.Piece;
import com.example.warehouseassistant.DataModel.PieceAdapter;
import com.example.warehouseassistant.DataModel.Transport;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class ZnpActivity extends AppCompatActivity {
    ArrayList<Piece> pieces;
    String[] type = { "Внутренний", "Внешний"};
    List<String> TCList = new ArrayList<>();
    Spinner typeZnP;
    Spinner TCspinner;
    String idUser;
    TextView ceh;
    TextView sklad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znp);

        Intent intent = getIntent();
        pieces = (ArrayList<Piece>) intent.getSerializableExtra("PiecesList");
        idUser = intent.getStringExtra("idUser");

        typeZnP = findViewById(R.id.spinner);
        TCspinner = findViewById(R.id.spinnerTC);
        ceh = findViewById(R.id.Ceh);
        sklad = findViewById(R.id.Sklad);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, R.layout.item_spinner, R.id.text_role, type);
        // Применяем адаптер к элементу spinner
        typeZnP.setAdapter(adapter1);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        typeZnP.setOnItemSelectedListener(itemSelectedListener);

        LoadTC();
    }

    public void LoadTC(){
        DataBase db = new DataBase();
        db.LoadTC(TCList, TCspinner, this);
    }

    public void cancel(View view){
        onBackPressed();
    }

    public void ShowPieces(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Список элементов");

// Создание списка элементов
        ListView listView = new ListView(this);
        PieceAdapter adapter = new PieceAdapter(this, pieces, true);
        listView.setAdapter(adapter);
        builder.setView(listView);

// Добавление кнопки "Закрыть"
        builder.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

// Отображение всплывающего окна
        AlertDialog dialog = builder.create();
        dialog.setTitle("Выбранные штуки");
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FunctionChoiceActivity.class);
        startActivity(intent);
    }

    public void ConfirmZnP(View view){
        String selectedTC = TCspinner.getSelectedItem().toString();
        String selectedType = typeZnP.getSelectedItem().toString();
        String selectedCeh = ceh.getText().toString();
        String selectedSklad = sklad.getText().toString();

        DataBase db = new DataBase();
        db.LoadRequest(idUser, pieces, selectedTC, selectedType, selectedCeh, selectedSklad, this);
    }
}