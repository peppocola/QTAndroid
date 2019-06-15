package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public final class ConnectionUtils {

    static void openConnectionDialog(@NonNull final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(R.string.alertConnection)
                .setMessage(R.string.alertConnectionMessage)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((AppCompatActivity) context).finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkConnection(context);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    static boolean absentConnection(@NonNull Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return !(activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    static void checkConnection(Context context) {
        if (ConnectionUtils.absentConnection(context)) {
            ConnectionUtils.openConnectionDialog(context);
        }
    }

}
