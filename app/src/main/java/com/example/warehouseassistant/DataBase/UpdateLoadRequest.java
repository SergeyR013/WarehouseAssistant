package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.warehouseassistant.Activities.FunctionChoiceActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UpdateLoadRequest extends AsyncTask<Void, Void, String> {
    String idUser;
    String status;
    String requestId;
    String statusUnload;
    Context context;

    public UpdateLoadRequest(String idUser, String status, String requestId, String statusUnload, Context context) {
        this.idUser = idUser;
        this.status = status;
        this.requestId = requestId;
        this.statusUnload = statusUnload;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/UpdateReloadRequest.php";
            URL url = new URL(link);
            String data = "";
            data += URLEncoder.encode("idUser", "UTF-8") + "="+idUser + "&";
            data += URLEncoder.encode("status", "UTF-8") + "="+status + "&";
            data += URLEncoder.encode("statusUnload", "UTF-8") + "="+statusUnload + "&";
            data += URLEncoder.encode("requestId", "UTF-8") + "="+requestId;
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
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, FunctionChoiceActivity.class);
        intent.putExtra("idUser", idUser);
        context.startActivity(intent);
    }
}
