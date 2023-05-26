package com.example.warehouseassistant.DataModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseassistant.Activities.userInserting;
import com.example.warehouseassistant.DataBase.DataBase;
import com.example.warehouseassistant.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> userList;
    private Context context;
    private String idUser;
    private Boolean isEdit;

    public UserAdapter(List<User> userList, Context context, String idUser, Boolean isEdit) {
        this.userList = userList;
        this.context = context;
        this.idUser = idUser;
        this.isEdit = isEdit;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_users, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);

        // Привяжите данные заказа к элементу списка в RecyclerView
        holder.textViewFullName.setText(user.getFullName());
        holder.textViewDomainName.setText("Логин: "+user.getDomain_name());
        holder.textViewDepartment.setText("Цех: "+user.getDepartment());
        holder.textViewUserGroup.setText("Группа: "+user.getUser_group());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String title = "";
                if (isEdit) title = "Редактирование пользователя";
                else title = "Удаление пользователя";
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title);

                // Настройка пользовательского макета
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_layout, null);
                builder.setView(dialogView);

                // Получение ссылок на кнопки
                Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);
                Button buttonReject = dialogView.findViewById(R.id.buttonReject);
                buttonConfirm.setVisibility(View.INVISIBLE);
                buttonReject.setVisibility(View.INVISIBLE);


                String msg = "";
                if (isEdit) msg = "Вы точно хотите редактировать пользователя: ";
                else msg = "Вы точно хотите удалить пользователя: ";
                // Получение ссылки на данные пользователя
                String fullName = user.getFullName(); // Замените на фактическое значение имени пользователя

                // Создание сообщения с динамическим именем пользователя
                String message = msg + fullName + "?";
                builder.setMessage(message);

                String positive = "";
                if (isEdit) {
                    positive = "Редактировать";
                }
                else positive = "Удалить";


                // Добавление кнопки "Закрыть"
                builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isEdit){
                            DataBase db = new DataBase();
                            db.DeleteUser(idUser, user.getId(), context);
                        } else{
                            Intent intent = new Intent(context, userInserting.class);
                            intent.putExtra("idUser", idUser);
                            intent.putExtra("isInsert", false);
                            intent.putExtra("User", user);
                            context.startActivity(intent);
                        }
                    }
                });

                // Добавление кнопки "Закрыть"
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

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName;
        TextView textViewDomainName;
        TextView textViewDepartment;
        TextView textViewUserGroup;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewDomainName = itemView.findViewById(R.id.textViewDomainName);
            textViewDepartment = itemView.findViewById(R.id.textViewDepartment);
            textViewUserGroup = itemView.findViewById(R.id.textViewUserGroup);
        }
    }
}
