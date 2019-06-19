package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class AskData extends AppCompatActivity {

    private int ID;
    private String radius = "";
    private Button button;
    private Spinner spinner;
    private EditText askRadius;
    private boolean enabled = false;

    public static final String DEFAULT_SPINNER = "--------";
    public static final int NEW_CLUSTER = 1;
    public static final int FILE_CLUSTER = 2;

    public static void openAskData(Context context, Bundle bundle) {
        ActivityUtils.openWithParams(AskData.class, context, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());

        Bundle b = getIntent().getExtras();
        ID = -1;
        if (b != null)
            ID = b.getInt("type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_data);

        TextView textView = findViewById(R.id.titolox);
        System.out.println(ID);
        if (ID == NEW_CLUSTER) {
            textView.setText(R.string.newcluster);
        } else if (ID == FILE_CLUSTER) {
            textView.setText(R.string.filecluster);
        } else finish();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        button = findViewById(R.id.eseguifc);
        setButton(button, AskData.this);

        spinner = findViewById(R.id.spinner);
        askRadius = findViewById(R.id.insRadius);

        askRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                radius = askRadius.getText().toString();
                enabled = !(spinner.getSelectedItem().toString().equals(DEFAULT_SPINNER)) && !radius.isEmpty();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                R.layout.color_spinner_layout);

        ConnectionUtils.checkConnection(this);
        ConnectionHandler c = new ConnectionHandler(this);
        try {
            System.out.println("executing");
            if (c.execute(ConnectionHandler.GET_TABLES).get().contains(ConnectionHandler.DONE)) {
                System.out.println("executed");
                LinkedList<String> tables = c.getTables();
                tables.add(0, DEFAULT_SPINNER);
                adapter.addAll(tables);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle(R.string.ServerUnreachableTitle)
                        .setMessage(R.string.ServerUnreachableMessage)
                        .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.openMainActivity(AskData.this);
                                (AskData.this).finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                enabled = !(spinner.getSelectedItem().toString().equals(DEFAULT_SPINNER)) && !radius.isEmpty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    protected void setButton(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enabled) {
                    ConnectionUtils.checkConnection(context);

                    try {
                        ConnectionHandler c = new ConnectionHandler(context);
                        switch (ID) {
                            case NEW_CLUSTER:
                                c.execute(ConnectionHandler.LEARN_DB, spinner.getSelectedItem().toString(), radius).get();
                                break;
                            case FILE_CLUSTER:
                                c.execute(ConnectionHandler.LEARN_FILE, spinner.getSelectedItem().toString(), radius).get();
                                break;
                            default:
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("result", c.getResult());
                        bundle.putInt("type", ID);

                        DisplayResults.openDisplayResults(AskData.this, bundle);

                    } catch (Exception e) {
                        System.out.println("Bottone: " + e.getMessage());
                        e.printStackTrace();
                    }

                } else
                    Toast.makeText(getApplicationContext(), R.string.fill, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ProgressBar progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
