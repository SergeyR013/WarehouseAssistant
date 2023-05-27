package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.warehouseassistant.Activities.FunctionChoiceActivity;
import com.example.warehouseassistant.Activities.adminPanel;
import com.example.warehouseassistant.DataModel.Piece;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CreateIsolationTask extends AsyncTask<Void, Void, String> {
    private String pieceId;
    private String idUser;
    private String reason;
    private Context context;

    public CreateIsolationTask(String pieceId, String idUser, String reason, Context context) {
        this.pieceId = pieceId;
        this.idUser = idUser;
        this.reason = reason;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/CreateIsolation.php";

            String data = URLEncoder.encode("idPiece", "UTF-8") + "=" + pieceId + "&";
            data += URLEncoder.encode("idUser", "UTF-8") + "=" + idUser + "&";
            data += URLEncoder.encode("reason", "UTF-8") + "=" + reason;

            URL url = new URL(link);
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
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, FunctionChoiceActivity.class);
            intent.putExtra("idUser", idUser);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Ошибка!", Toast.LENGTH_SHORT).show();
        }
    }
}

