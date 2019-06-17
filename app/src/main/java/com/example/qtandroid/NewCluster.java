package com.example.qtandroid;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.UnknownHostException;

public class NewCluster extends AppCompatActivity {

    ConnectionHandler conn;
    Button vai;

    public static void openNewCluster(Context context) {
        ActivityUtils.open(NewCluster.class, context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cluster);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        vai = findViewById(R.id.eseguinc);
        try {
            conn = new ConnectionHandler();
            System.out.println("TuttoApposto");
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            Toast.makeText(this, "Connessione al server non riuscita", Toast.LENGTH_SHORT).show();
        }
        System.out.println("NON sono ESPLOSO");

        vai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    conn.storeTableFromDb("playtennis");
                    System.out.println(conn.learningFromDbTable(2.0));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });
    }




}
