package com.example.qtandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FileCluster extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_cluster);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
