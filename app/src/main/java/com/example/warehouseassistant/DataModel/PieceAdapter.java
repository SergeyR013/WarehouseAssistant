package com.example.warehouseassistant.DataModel;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehouseassistant.R;

import java.util.List;

public class PieceAdapter extends ArrayAdapter<Piece> {
    private Context context;
    private List<Piece> pieces;
    private SparseBooleanArray selectedItems;
    private Boolean isAlert;

    public PieceAdapter(Context context, List<Piece> pieces, Boolean isAlert) {
        super(context, 0, pieces);
        this.context = context;
        this.pieces = pieces;
        this.selectedItems = new SparseBooleanArray();
        this.isAlert = isAlert;
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
        pieceIdTextView.setText(piece.getPieceId());

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
}
