package com.example.warehouseassistant.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.DataModel.OnPiecesLoadedListener;
import com.example.warehouseassistant.DataModel.Piece;
import com.example.warehouseassistant.DataModel.PieceAdapter;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class PiecesActivity extends AppCompatActivity implements OnPiecesLoadedListener {

    ListView lvPieces;
    PieceAdapter adapter;
    String orderId, positionId;
    List<Piece> pieceList = new ArrayList<>();
    String idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pieces);

        // Получение данных из интента
        Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getStringExtra("orderId");
            positionId = intent.getStringExtra("positionId");
            idUser = intent.getStringExtra("idUser");
        }

        lvPieces = findViewById(R.id.lvPieces);
        lvPieces.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        DataBase db = new DataBase();
        db.LoadPices(pieceList, orderId, positionId, adapter, lvPieces, this, this, false);

    }

    @Override
    public void onPiecesLoaded(PieceAdapter adapter) {
        this.adapter = adapter;
        lvPieces.setAdapter(adapter);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, OrdersActivity.class);
        intent.putExtra("iduser", idUser);
        startActivity(intent);
    }

    public void goContinue(View view){
        if (adapter.getSelectedItems().size() < 1){
            Toast.makeText(this, "Выберите штуки!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ZnpActivity.class);
        ArrayList<Piece> pieces = new ArrayList<>();
        SparseBooleanArray selectedItems = adapter.getSelectedItems();
        for (int i = 0; i < selectedItems.size(); i++) {
            int position = selectedItems.keyAt(i);
            if (selectedItems.valueAt(i)) {
                Piece selectedPiece = pieceList.get(position);
                pieces.add(selectedPiece);
            }
        }
        intent.putExtra("PiecesList", pieces);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }
}