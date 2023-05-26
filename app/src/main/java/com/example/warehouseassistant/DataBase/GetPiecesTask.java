package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.warehouseassistant.DataModel.OnPiecesLoadedListener;
import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.Piece;
import com.example.warehouseassistant.DataModel.PieceAdapter;
import com.example.warehouseassistant.R;

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

public class GetPiecesTask extends AsyncTask<Void, Void, String> {
    private List<Piece> picesList;
    private String orderId;
    private String positionId;
    private PieceAdapter adapter;
    private OnPiecesLoadedListener listener;
    private ListView lvPieces;
    private Context context;
    private Boolean isAlert;

    public GetPiecesTask(List<Piece> picesList, String orderId, String positionId, PieceAdapter adapter, ListView lvPieces, Context context, OnPiecesLoadedListener listener, Boolean isAlert) {
        this.picesList = picesList;
        this.orderId = orderId;
        this.positionId = positionId;
        this.adapter = adapter;
        this.lvPieces = lvPieces;
        this.context = context;
        this.listener = listener;
        this.isAlert = isAlert;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/GetPieces.php";
            URL url = new URL(link);
            String data = URLEncoder.encode("orderId", "UTF-8") + "="+orderId+"&";
            data += URLEncoder.encode("positionId", "UTF-8") + "="+positionId;
            URLConnection con = url.openConnection();
            con.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write(data);
            wr.flush();

            wr.close();

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
                    piece.setPieceId("ID: " + jsonObject.getString("id"));
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

                adapter = new PieceAdapter(context, picesList, isAlert);
                adapter.notifyDataSetChanged();

                if (listener != null){
                    listener.onPiecesLoaded(adapter);
                }
                //lvPieces.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки
        }
    }

}
