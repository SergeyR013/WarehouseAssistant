package com.example.warehouseassistant.DataModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseassistant.Activities.OrdersActivity;
import com.example.warehouseassistant.Activities.PiecesActivity;
import com.example.warehouseassistant.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private List<Order> orderList;
    private Context context;
    private String idUser;

    public OrderAdapter(List<Order> orderList, Context context, String idUser) {
        this.orderList = orderList;
        this.context = context;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Привяжите данные заказа к элементу списка в RecyclerView
        holder.textViewOrderId.setText("Заказ №"+order.getOrderId());
        holder.textViewPositionId.setText("Позиция: "+order.getPositionId());
        holder.textViewWeight.setText("Вес: "+order.getWeight());
        holder.textViewDelivery.setText("Доставка: "+order.getDelivery());
        holder.textViewDeliveryDate.setText("Дата доставки: "+order.getDeliveryDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PiecesActivity.class);
                intent.putExtra("orderId", order.getOrderId());
                intent.putExtra("positionId", order.getPositionId());
                intent.putExtra("idUser", idUser);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId;
        TextView textViewPositionId;
        TextView textViewWeight;
        TextView textViewDelivery;
        TextView textViewDeliveryDate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewPositionId = itemView.findViewById(R.id.textViewPositionId);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            textViewDelivery = itemView.findViewById(R.id.textViewDelivery);
            textViewDeliveryDate = itemView.findViewById(R.id.textViewDeliveryDate);
        }
    }




}
