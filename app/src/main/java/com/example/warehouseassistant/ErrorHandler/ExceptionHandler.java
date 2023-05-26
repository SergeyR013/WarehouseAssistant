package com.example.warehouseassistant.ErrorHandler;


import android.content.Context;

public class ExceptionHandler {

    public void HandleException(Exception ex, Context activity){
        ErrorNotification notification = new ErrorNotification();
        notification.Notify(ex, activity);
    }

}
