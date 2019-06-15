package com.example.qtandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FileCluster extends AppCompatActivity {

    public static void openFileCluster(Context context) {
        ActivityUtils.open(FileCluster.class, context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_cluster);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
