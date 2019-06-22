package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

final class ConnectionUtils {

    private ConnectionUtils() {
    }

    public static void openConnectionDialog(@NonNull final Context context) {
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
                        if (absentConnection(context)) {
                            openConnectionDialog(context);
                        } else
                            Toast.makeText(context, "Connessione stabilita! Premi VAI!", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void openConnectionDialog(@NonNull final Context context, final RadioGroup select) {
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
                        if (ConnectionUtils.absentConnection(context)) {
                            dialogInterface.dismiss();
                            ConnectionUtils.openConnectionDialog(context, select);
                        } else {
                            Bundle bundle = new Bundle();
                            if (select.getCheckedRadioButtonId() == R.id.newcluster) {
                                bundle.putInt("key", AskData.NEW_CLUSTER);
                                AskData.openAskData(context, bundle);
                            } else if (select.getCheckedRadioButtonId() == R.id.filecluster) {
                                bundle.putInt("key", AskData.FILE_CLUSTER);
                                AskData.openAskData(context, bundle);
                            } else dialogInterface.cancel();
                        }

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static boolean absentConnection(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return !(activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        } else {
            return true;
        }
    }

    public static void checkConnection(Context context) {
        if (ConnectionUtils.absentConnection(context)) {
            ConnectionUtils.openConnectionDialog(context);
        }
    }

}
