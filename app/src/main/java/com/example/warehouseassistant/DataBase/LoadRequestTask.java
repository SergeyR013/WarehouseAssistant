package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.warehouseassistant.Activities.FunctionChoiceActivity;
import com.example.warehouseassistant.DataModel.Piece;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.SelectionKey;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class LoadRequestTask extends AsyncTask<Void, Void, String> {
    List<Piece> pieces;
    String idUser;
    String TC;
    String requestType;
    String Ceh;
    String Sklad;
    Context context;

    public LoadRequestTask(String idUser, List<Piece> pieces, String TC, String requestType, String Ceh, String Sklad, Context context) {
        this.idUser = idUser;
        this.pieces = pieces;
        this.TC = TC;
        this.requestType = requestType;
        this.Ceh = Ceh;
        this.Sklad = Sklad;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String link = "http://sorazdx9.beget.tech/LoadRequest.php";
            URL url = new URL(link);
            String data = "";
            for (int i = 0; i < pieces.size() - 1; i++){
                data += URLEncoder.encode("idPiece"+i, "UTF-8") + "="+pieces.get(i).getPieceId().substring(4) + "&";
            }
            data += URLEncoder.encode("idPiece"+(pieces.size()-1), "UTF-8") + "="+pieces.get(pieces.size()-1).getPieceId().substring(4) + "&";
            data += URLEncoder.encode("idUser", "UTF-8") + "="+idUser + "&";
            data += URLEncoder.encode("TC", "UTF-8") + "="+TC + "&";
            data += URLEncoder.encode("requestType", "UTF-8") + "="+requestType + "&";
            data += URLEncoder.encode("sklad", "UTF-8") + "="+Sklad + "&";
            data += URLEncoder.encode("ceh", "UTF-8") + "="+Ceh;
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
