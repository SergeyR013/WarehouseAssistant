package com.example.warehouseassistant.DataModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseassistant.Activities.ZnpActivity;
import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.R;

import java.util.ArrayList;
import java.util.List;

public class ReloadRequestsAdapter extends RecyclerView.Adapter<ReloadRequestsAdapter.RequestsViewHolder>{

    private List<ReloadRequests> requestsList;
    private Context context;
    private String idUser;
    private Boolean isEdit;

    public ReloadRequestsAdapter(List<ReloadRequests> requestsList, Context context, String idUser, Boolean isEdit) {
        this.requestsList = requestsList;
        this.context = context;
        this.idUser = idUser;
        this.isEdit = isEdit;
    }

    @NonNull
    @Override
    public ReloadRequestsAdapter.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reload_req, parent, false);
        return new ReloadRequestsAdapter.RequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReloadRequestsAdapter.RequestsViewHolder holder, int position) {
        ReloadRequests req = requestsList.get(position);

        // Привяжите данные заказа к элементу списка в RecyclerView
        holder.textViewStatus.setText(req.getStatus_id());
        holder.textViewCreationDate.setText(req.getCreation_date());
        holder.textViewCreatedBy.setText(req.getCreated_by());
        holder.textViewRequestType.setText(req.getRequest_type());
        holder.textViewTC.setText(req.getTC());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit){
                    Toast.makeText(context, "Редактирование вызвано!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ZnpActivity.class);
                    intent.putExtra("idUser", idUser);
                    intent.putExtra("isEdit", isEdit);
                    intent.putExtra("selectedType", req.getRequest_type());
                    intent.putExtra("selectedTC", req.getTC());
                    intent.putExtra("idReq", req.getRequest_id());

                    context.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Подтверждение запроса на выгрузку");

                    // Настройка пользовательского макета
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_layout, null);
                    builder.setView(dialogView);

                    // Получение ссылок на кнопки
                    Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);
                    Button buttonReject = dialogView.findViewById(R.id.buttonReject);

                    // Добавление слушателей для кнопок
                    buttonConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBase db = new DataBase();
                            db.UpdateLoadRequest(idUser, "4", req.getRequest_id(), "Принят", context);
                        }
                    });

                    buttonReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBase db = new DataBase();
                            db.UpdateLoadRequest(idUser, "5", req.getRequest_id(), "Отклонен", context);
                        }
                    });

                    // Добавление кнопки "Закрыть"
                    builder.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    static class RequestsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStatus;
        TextView textViewCreationDate;
        TextView textViewCreatedBy;
        TextView textViewRequestType;
        TextView textViewTC;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewCreationDate = itemView.findViewById(R.id.textViewCreationDate);
            textViewCreatedBy = itemView.findViewById(R.id.textViewCreatedBy);
            textViewRequestType = itemView.findViewById(R.id.textViewRequestType);
            textViewTC = itemView.findViewById(R.id.textViewTC);
        }
    }
}
