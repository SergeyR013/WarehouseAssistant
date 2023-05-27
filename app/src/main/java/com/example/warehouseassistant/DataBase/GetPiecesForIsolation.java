package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.warehouseassistant.DataModel.Piece;
import com.example.warehouseassistant.DataModel.PieceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class GetPiecesForIsolation extends AsyncTask<Void, Void, String> {
    private List<Piece> picesList;
    private PieceAdapter adapter;
    private Context context;

    public GetPiecesForIsolation(List<Piece> picesList, PieceAdapter adapter, Context context) {
        this.picesList = picesList;
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/GetPiecesForIsolation.php";
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
                JSONArray jsonArray = new JSONArray(result); // jsonData - строка JSON с данными из файла GetPieces.php

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Piece piece = new Piece();
                    piece.setPieceId(jsonObject.getString("id"));
                    piece.setPieceNumber("Номер " + jsonObject.getString("item_number"));
                    piece.setBatch("Плавка: " + jsonObject.getString("batch"));
                    piece.setWarehouseId("Цех: " + jsonObject.getString("warehouse_id"));
                    piece.setLength("Длина: " + jsonObject.getString("length"));
                    piece.setWidth("Ширина: " + jsonObject.getString("width"));
                    piece.setWeight("Вес: " + jsonObject.getString("weight"));
                    piece.setOwner_number("Владелец: " + jsonObject.getString("owner_number"));
                    piece.setProductType("Тип: " + jsonObject.getString("type"));
                    picesList.add(piece);
                }

                adapter.setPieceList(picesList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки
        }
    }
}
