package com.example.warehouseassistant.DataBase;

import android.os.AsyncTask;

import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.OrderAdapter;
import com.example.warehouseassistant.DataModel.User;
import com.example.warehouseassistant.DataModel.UserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class GetUserTask extends AsyncTask<Void, Void, String> {

    private List<User> userList;
    private UserAdapter userAdapter;

    public GetUserTask(List<User> userList, UserAdapter userAdapter) {
        this.userList = userList;
        this.userAdapter = userAdapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/GetUser(delete).php";
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
                    User user = new User();
                    user.setId(jsonObject.getString("id"));
                    user.setFullName(jsonObject.getString("full_name"));
                    user.setDomain_name(jsonObject.getString("domain_name"));
                    user.setUser_group(jsonObject.getString("user_group"));
                    user.setDepartment(jsonObject.getString("department"));
                    userList.add(user);
                }

                userAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки
        }
    }
}
