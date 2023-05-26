package com.example.warehouseassistant.DataBase;

import android.os.AsyncTask;

import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.OrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class GetOrdersTask extends AsyncTask<Void, Void, String> {

    private List<Order> orderList;
    private OrderAdapter orderAdapter;

    public GetOrdersTask(List<Order> orderList, OrderAdapter orderAdapter) {
        this.orderList = orderList;
        this.orderAdapter = orderAdapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/GetOrders.php";
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result); // jsonData - строка JSON с данными из файла GetOrders.php

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setOrderId(jsonObject.getString("order_id"));
                    order.setPositionId(jsonObject.getString("position_id"));
                    order.setWeight(jsonObject.getString("weight"));
                    order.setDelivery(jsonObject.getString("delivery"));
                    order.setDeliveryDate(jsonObject.getString("delivery_date"));
                    orderList.add(order);
                }

                orderAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки
        }
    }
}