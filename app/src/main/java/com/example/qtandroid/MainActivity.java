package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class MainActivity extends AppCompatActivity {

    private Switch DarkSwitch;

    private RadioGroup select;
    private RadioButton selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button buttonDetails;
        Button buttonCluster;

        setTheme(ThemeUtils.defaultTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DarkSwitch = findViewById(R.id.DarkSwitch);
        DarkSwitch.setChecked(AppCompatDelegate.getDefaultNightMode()==MODE_NIGHT_YES);

        listen();

        buttonDetails = findViewById(R.id.details);
        setDetailsButton(buttonDetails);

        select = findViewById(R.id.select);
        buttonCluster = findViewById(R.id.esegui);
        setClusterButton(buttonCluster);

        if (absentConnection()) {
            openConnectionDialog();
        }


    }

    protected boolean absentConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return !(activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }


    protected void setDetailsButton(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetails();
            }
        });
    }

    protected void setClusterButton(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(select.getCheckedRadioButtonId()==R.id.newcluster){
                    openNewCluster();
                } else if (select.getCheckedRadioButtonId() == R.id.filecluster) openFileCluster();

            }
        });
    }

    public void checkRadio(View v) {
        int radioId = select.getCheckedRadioButtonId();
        selected = findViewById(radioId);
        Toast.makeText(this, "Hai selezionato: " + selected.getText(), Toast.LENGTH_SHORT).show();

    }


    protected void openDetails(){
        Intent i = new Intent(this, Details.class);
        startActivity(i);

    }

    protected void openNewCluster(){
        Intent i = new Intent( this, NewCluster.class);
        startActivity(i);
    }

    protected void openFileCluster(){
        Intent i = new Intent( this, FileCluster.class);
        startActivity(i);
    }


    public void listen(){
        DarkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
                    recreate();
                }else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
                    recreate();
                }
            }
        });
    }

    protected void openConnectionDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder
                .setTitle(R.string.alertConnection)
                .setMessage(R.string.alertConnectionMessage)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!absentConnection()) {
                            openConnectionDialog();
                        }
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder
                .setMessage(R.string.exit)
                .setCancelable(true)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
