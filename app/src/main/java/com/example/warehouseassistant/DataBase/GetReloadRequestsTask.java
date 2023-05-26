package com.example.warehouseassistant.DataBase;

import android.os.AsyncTask;

import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.OrderAdapter;
import com.example.warehouseassistant.DataModel.ReloadRequests;
import com.example.warehouseassistant.DataModel.ReloadRequestsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class GetReloadRequestsTask extends AsyncTask<Void, Void, String> {
    private List<ReloadRequests> requestsList;
    private ReloadRequestsAdapter reqAdapter;

    public GetReloadRequestsTask(List<ReloadRequests> requestsList, ReloadRequestsAdapter reqAdapter) {
        this.requestsList = requestsList;
        this.reqAdapter = reqAdapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/GetReloadRequests.php";
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
                    ReloadRequests req = new ReloadRequests();
                    req.setRequest_id(jsonObject.getString("request_id"));
                    req.setStatus_id(jsonObject.getString("status"));
                    req.setCreation_date("Дата создания: " + jsonObject.getString("creation_date"));
                    req.setCreated_by("Создан: " + jsonObject.getString("full_name"));
                    req.setRequest_type("Вид: " + jsonObject.getString("request_type"));
                    req.setTC("ТС: " + jsonObject.getString("vehicle_number"));
                    requestsList.add(req);
                }

                reqAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки
        }
    }
}
