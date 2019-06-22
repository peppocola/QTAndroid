package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayResults extends AppCompatActivity {

    int type;
    String result;

    public static void openDisplayResults(Context context, Bundle bundle) {
        ActivityUtils.openWithParams(DisplayResults.class, context, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtils.defaultTheme());
        Bundle bundle = getIntent().getExtras();
        result = bundle.getString("result");
        type = bundle.getInt("type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = findViewById(R.id.textView);

        boolean isNull = result == null;
        boolean isEmpty = !isNull && result.equals("empty");  //nullpointer
        boolean isFull = !isNull && !isEmpty && result.equals("full");

        String title = "";
        String message = "";

        if (isFull) {
            title = "Raggio troppo piccolo";
            message = "Tutti i dati sono finiti nello stesso cluster, prova a selezionare un raggio più piccolo.";

        } else if (isEmpty) {
            title = "Insieme vuoto!";
            message = "Nessun dato da clusterizzare...\nProva a selezionare un'altra tabella";
        }

        if (isEmpty || isFull) { //da testare

            final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayResults.this);
            builder
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            dialogInterface.cancel();
                            MainActivity.openMainActivity(DisplayResults.this);
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            dialogInterface.cancel();
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", type);
                            AskData.openAskData(DisplayResults.this, bundle);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            textView.setText(result);
        }

        Button home = findViewById(R.id.home);
        Button back = findViewById(R.id.back);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionHandler2.getInstance().disconnect();
                MainActivity.openMainActivity(DisplayResults.this);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                AskData.openAskData(DisplayResults.this, bundle);
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
