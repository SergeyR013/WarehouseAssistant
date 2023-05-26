package com.example.warehouseassistant.ErrorHandler;

import android.content.Context;
import android.widget.Toast;

public class ErrorNotification {

    public void Notify(Exception ex, Context activity){
        Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
