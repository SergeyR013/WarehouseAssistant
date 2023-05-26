package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.warehouseassistant.Activities.adminPanel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class EditUsertask extends AsyncTask<String, Void, String> {
    private String password;
    private String login;
    private String role;
    private String fullName;
    private String department;
    private String id;
    private String idUser;
    private Context func;


    public EditUsertask(String password, String login, String role, String fullName, String department, String id, String idUser, Context func) {
        this.password = password;
        this.login = login;
        this.role = role;
        this.fullName = fullName;
        this.department = department;
        this.id = id;
        this.idUser = idUser;
        this.func = func;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String link = "http://sorazdx9.beget.tech/EditUser.php";
            String data = URLEncoder.encode("login", "UTF-8") + "=" + login + "&";
            data += URLEncoder.encode("password", "UTF-8") + "=" + password + "&";
            data += URLEncoder.encode("role", "UTF-8") + "=" + role + "&";
            data += URLEncoder.encode("FIO", "UTF-8") + "=" + fullName + "&";
            data += URLEncoder.encode("dep", "UTF-8") + "=" + department + "&";
            data += URLEncoder.encode("idUser", "UTF-8") + "=" + idUser + "&";
            data += URLEncoder.encode("id", "UTF-8") + "=" + id;
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write(data);
            wr.flush();

            wr.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();

            reader.lines().findFirst().ifPresent(sb::append);


            reader.close();

            return sb.toString();

        }
        catch (Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(func, result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(func, adminPanel.class);
        intent.putExtra("idUser", idUser);
        func.startActivity(intent);
    }
}
