package com.example.qtandroid;

import android.content.DialogInterface;
import android.content.Intent;
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
        setDetailsButton(buttonDetails);

        select = findViewById(R.id.select);
        buttonCluster = findViewById(R.id.esegui);
        setClusterButton(buttonCluster);

        ConnectionUtils.checkConnection(MainActivity.this);


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
