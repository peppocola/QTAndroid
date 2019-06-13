package com.example.qtandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class MainActivity extends AppCompatActivity {

    private Switch DarkSwitch;
    private Button buttonDetails;

    private RadioGroup select;
    private RadioButton selected;
    private Button buttonCluster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
                }else openFileCluster();

            }
        });
    }

    protected void checkRadio(View v){
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




}
