package com.tridev.chillflix.utilities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.tridev.chillflix.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(dialog);

            AppCompatButton btnRetry = dialog.findViewById(R.id.btn_retry);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


            btnRetry.setOnClickListener(v -> {
                alertDialog.dismiss();
                onReceive(context, intent);
            });

        }
    }
}
