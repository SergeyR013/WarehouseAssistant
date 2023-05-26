package com.example.warehouseassistant.ErrorHandler;


import android.content.Context;


public class ErrorHandler {
    Context activity;
    ExceptionHandler exception;
    Exception ex;

    public ErrorHandler(Context activity, Exception ex) {
        this.activity = activity;
        this.ex = ex;
        exception = new ExceptionHandler();
    }

    public void HandleError(){
        exception.HandleException(ex, activity);
    }
}
