package com.example.qtandroid;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayResults extends AppCompatActivity {

    int type;
    String result;

    public static void openDisplayResults(Context context, Bundle bundle) {
        ActivityUtils.openWithParams(DisplayResults.class, context, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        result = bundle.getString("result");
        type = bundle.getInt("type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = findViewById(R.id.textView);
        textView.setText(result);

        Button home = findViewById(R.id.home);
        Button back = findViewById(R.id.back);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.openMainActivity(DisplayResults.this);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {

        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        AskData.openAskData(DisplayResults.this, bundle);

        super.onBackPressed();
    }
}
