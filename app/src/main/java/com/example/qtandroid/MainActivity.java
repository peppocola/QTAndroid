package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

        ThemeUtils.listen(MainActivity.this, DarkSwitch);

        buttonDetails = findViewById(R.id.details);
        setDetailsButton(buttonDetails, this);

        select = findViewById(R.id.select);
        buttonCluster = findViewById(R.id.esegui);
        setClusterButton(buttonCluster, this);

        //ConnectionUtils.checkConnection(MainActivity.this); in the main screen connection is not necessary


    }

    protected void setDetailsButton(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details.openDetails(context);
            }
        });
    }

    protected void setClusterButton(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionUtils.absentConnection(MainActivity.this)) {
                    ConnectionUtils.openConnectionDialog(MainActivity.this);
                } else if (select.getCheckedRadioButtonId() == R.id.newcluster) {
                    NewCluster.openNewCluster(context);
                } else if (select.getCheckedRadioButtonId() == R.id.filecluster)
                    FileCluster.openFileCluster(context);

            }
        });
    }

    public void checkRadio(View v) {
        int radioId = select.getCheckedRadioButtonId();
        selected = findViewById(radioId);
        Toast.makeText(this, "Hai selezionato: " + selected.getText(), Toast.LENGTH_SHORT).show();

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
