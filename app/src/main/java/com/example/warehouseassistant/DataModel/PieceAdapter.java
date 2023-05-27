package com.example.warehouseassistant.DataModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehouseassistant.Activities.FunctionChoiceActivity;
import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.R;

import java.util.List;

public class PieceAdapter extends ArrayAdapter<Piece> {
    private Context context;
    private List<Piece> pieces;
    private SparseBooleanArray selectedItems;
    private Boolean isAlert;
    private Boolean isIsolation = false;
    private String idUser;

    public PieceAdapter(Context context, List<Piece> pieces, Boolean isAlert) {
        super(context, 0, pieces);
        this.context = context;
        this.pieces = pieces;
        this.selectedItems = new SparseBooleanArray();
        this.isAlert = isAlert;
    }

    public PieceAdapter(Context context, List<Piece> pieces, Boolean isAlert, Boolean isIsolation, String idUser) {
        super(context, 0, pieces);
        this.context = context;
        this.pieces = pieces;
        this.selectedItems = new SparseBooleanArray();
        this.isAlert = isAlert;
        this.isIsolation = isIsolation;
        this.idUser = idUser;
    }

    @Override
    public int getCount() {
        return pieces.size();
    }

    @Override
    public Piece getItem(int position) {
        return pieces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            if (!isAlert){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_piece, parent, false);}
            else{
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_piece_2, parent, false);
            }
        }

        // Получение текущего элемента списка
        Piece piece = pieces.get(position);

        // Настройка отображения данных элемента списка
        TextView pieceIdTextView = convertView.findViewById(R.id.tvPieceId);
        pieceIdTextView.setText("ID: "+piece.getPieceId());

        TextView pieceNumberTextView = convertView.findViewById(R.id.tvPieceNumber);
        pieceNumberTextView.setText(piece.getPieceNumber());

        TextView batchTextView = convertView.findViewById(R.id.tvBatch);
        batchTextView.setText(piece.getBatch());

        TextView tvWarehouseId = convertView.findViewById(R.id.tvWarehouseId);
        tvWarehouseId.setText(piece.getWarehouseId());

        TextView tvLength = convertView.findViewById(R.id.tvLength);
        tvLength.setText(piece.getLength());

        TextView tvWidth = convertView.findViewById(R.id.tvWidth);
        tvWidth.setText(piece.getWidth());

        TextView tvWeight = convertView.findViewById(R.id.tvWeight);
        tvWeight.setText(piece.getWeight());

        TextView tvOwnerNumber = convertView.findViewById(R.id.tvOwnerNumber);
        tvOwnerNumber.setText(piece.getOwner_number());

        TextView tvProductType = convertView.findViewById(R.id.tvProductType);
        tvProductType.setText(piece.getProductType());

        if (!isAlert){
            CheckBox checkbox = convertView.findViewById(R.id.checkbox);
            checkbox.setOnCheckedChangeListener(null);
            // Установите состояние чекбокса в соответствии с выбором элемента
            checkbox.setChecked(selectedItems.get(position, false));
            // Добавьте остальные поля класса Piece аналогичным образом

            // Установите обработчик для чекбокса
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    toggleSelection(position);
                }
            });
        } else{
            if (isIsolation){
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Оформление карантина");

                        // Настройка пользовательского макета
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_quarantine, null);
                        builder.setView(dialogView);

                        // Получение ссылок на элементы в пользовательском макете
                        EditText etReason = dialogView.findViewById(R.id.etReason);

                        // Добавление кнопки "Оформить карантин"
                        builder.setPositiveButton("Оформить карантин", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String reason = etReason.getText().toString();
                                String idPiece = piece.getPieceId();

                                DataBase db = new DataBase();
                                db.CreateIsolation(idPiece, idUser, reason, context);
                            }
                        });

                        // Добавление кнопки "Отмена"
                        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        }
        return convertView;
    }

    // Переключение выбора элемента списка по позиции
    private void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyDataSetChanged();
    }

    // Получение выбранных элементов списка
    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }

    public void setPieceList(List<Piece> pieceList) {
        this.pieces = pieceList;
        notifyDataSetChanged();
    }
}
