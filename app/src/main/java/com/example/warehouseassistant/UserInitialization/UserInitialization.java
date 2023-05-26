package com.example.warehouseassistant.UserInitialization;

import android.content.Context;


import java.security.NoSuchAlgorithmException;

public class UserInitialization {
    private final Input input;
    private Initialization init;

    public UserInitialization(){
        input = new Input();
    }

    public void StartInput(String login, String password, String role, String fullname, String department, Context mainActivity, Class<?> second) throws NoSuchAlgorithmException {
        input.SetData(password, login, role, fullname, department);
        init = new Initialization(input.getUsername(), input.getPassword());
        SetContent(mainActivity, second);
    }

    public void CheckAccess(){
        init.execute();
    }

    public void SetContent(Context mainActivity, Class<?> second){
        init.setMainActivity(mainActivity);
        init.setSecond(second);
    }

    public String getHashPassword(){
        return input.getPassword();
    }

    public String getlogin(){
        return input.getUsername();
    }

    public String getRole(){
        return input.getRole();
    }

    public String getDepartment(){
        return input.getDepartment();
    }

    public String getFullName(){
        return input.getFullName();
    }
}
