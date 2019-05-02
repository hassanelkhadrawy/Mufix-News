package com.example.finalmufixapp.Activites;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Progress_Dialog {
    SweetAlertDialog pDialog;

Context context;

    public Progress_Dialog(Context context) {
        this.context = context;
    }

    private void SweetAlertDialog(){

        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

    }
}
