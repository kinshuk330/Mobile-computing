package com.example.libraso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

public class Loading extends BroadcastReceiver {
    private Activity activity;
    private AlertDialog progressDialog;
    private boolean active=false;

    @Override
    public void onReceive(Context context, Intent intent) {
        activity= (Activity) context;
        if(intent.getAction()=="START_LOADING" && active==false){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater infl=activity.getLayoutInflater();
        builder.setView(infl.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        progressDialog=builder.create();
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        active=true;
        System.out.println("start");}
        else if(intent.getAction()=="STOP_LOADING" && active==true){
            progressDialog.dismiss();
            active=false;
            System.out.println("stop");
        }
    }
}
