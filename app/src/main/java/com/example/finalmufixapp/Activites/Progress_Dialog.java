package com.example.finalmufixapp.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Progress_Dialog {
    ProgressDialog pDialog;

Context context;

    public Progress_Dialog(Context context) {
        this.context = context;
    }

     void SweetAlertDialog(){

        pDialog = new ProgressDialog(context);
//        pDialog..setBarColor(Color.parseColor("#ee6e2c"));
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

    }
}
