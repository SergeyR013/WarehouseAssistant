package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.Piece;
import com.example.warehouseassistant.DataModel.PieceAdapter;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class IsolationActivity extends AppCompatActivity {

    private String idUser;
    ListView lvPieces;
    PieceAdapter adapter;
    List<Piece> pieceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isolation);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");

        lvPieces = findViewById(R.id.lvPieces);
        lvPieces.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        adapter = new PieceAdapter(this, pieceList, true, true, idUser);
        lvPieces.setAdapter(adapter);

        LoadPieces();
    }

    public void LoadPieces(){
        DataBase db = new DataBase();
        db.GetPiecesForIsolation(pieceList, adapter, this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FunctionChoiceActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}