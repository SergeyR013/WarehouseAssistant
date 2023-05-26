package com.example.warehouseassistant.UserInitialization;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.warehouseassistant.Activities.adminPanel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
public class Initialization extends AsyncTask<String, Void, String> {
    private boolean access;
    private final String login;
    private final String password;
    private String isActive;
    private Context mainActivity;
    private Class<?> second;

    public Initialization(String login, String password){
        this.login = login;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String link = "http://sorazdx9.beget.tech/login.php";
            String data = URLEncoder.encode("login", "UTF-8") + "="+login;
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

        }
        catch (Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject jsonResponse = null;
        String userPassword;
        String currentRole = "none";
        String idUser = "none";
        try {
            jsonResponse = new JSONObject(result);
            idUser = jsonResponse.getString("id");
            userPassword = jsonResponse.getString("password");
            currentRole = jsonResponse.getString("user_group");
            isActive = jsonResponse.getString("is_active");
            access = password.equals(userPassword);

        } catch (JSONException e) {
            access = false;
        }
        finally {
            CheckAccess(mainActivity, second, currentRole, idUser, isActive);
        }
    }

    public void CheckAccess(Context main, Class<?> second, String userRole, String idUser, String isActive){
        String msg;
        if (access && isActive.equals("1")){
            switch (userRole){
                case"1":{
                    Intent intent = new Intent(main, adminPanel.class);
                    intent.putExtra("idUser", idUser);
                    main.startActivity(intent);
                } break;
                case "2":{
                    Intent intent = new Intent(main, second);
                    intent.putExtra("idUser", idUser);
                    main.startActivity(intent);
                } break;
            }
            msg = "Доступ разрешен!";
        } else {
            msg = "Доступ запрещен!";
        }
        Toast.makeText(main, msg, Toast.LENGTH_SHORT).show();
    }

    public void setMainActivity(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setSecond(Class<?> second) {
        this.second = second;
    }
}
