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

    private AlertDialog alertDialog;

    public static final String DEFAULT_SPINNER = "--------";
    public static final int NEW_CLUSTER = 1;
    public static final int FILE_CLUSTER = 2;

    public static void openAskData(Context context, Bundle bundle) {
        System.out.println("NOn lo so" + context);
        ActivityUtils.openWithParams(AskData.class, context, bundle);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_data);

        Bundle b = getIntent().getExtras();
        ID = -1;
        if (b != null)
            ID = b.getInt("type");



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
        try {
            System.out.println("executing");
            if (!ConnectionHandler2.getInstance().isConnected()) {
                ConnectionHandler2.getInstance().setAddres("192.168.0.7");
                ConnectionHandler2.getInstance().setPort(8080);
                ConnectionHandler2.getInstance().connect();
            }
            if (ConnectionHandler2.getInstance().isConnected()) {
                System.out.println("executed");
                LinkedList<String> tables = ConnectionHandler2.getInstance().getTables();
                tables.add(0, DEFAULT_SPINNER); //nullpointer
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
                        })
                        .setCancelable(false);

                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
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

                    if (Double.parseDouble(radius) > 0) {
                        ConnectionUtils.checkConnection(context);
                        String result = "";
                        Bundle bundle = new Bundle();

                        try {

                            switch (ID) {
                                case NEW_CLUSTER:
                                    result = ConnectionHandler2.getInstance().learnDB(spinner.getSelectedItem().toString(), Double.parseDouble(radius));
                                    bundle.putInt("type", AskData.NEW_CLUSTER);
                                    break;
                                case FILE_CLUSTER:
                                    result = ConnectionHandler2.getInstance().learnFile(spinner.getSelectedItem().toString(), Double.parseDouble(radius));
                                    bundle.putInt("type", AskData.FILE_CLUSTER);
                                    break;
                                default:
                            }
                            bundle.putString("result", result);
                            DisplayResults.openDisplayResults(context, bundle);

                        } catch (Exception e) {
                            System.out.println("Bottone: " + e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.zeroradius, Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), R.string.fill, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ConnectionHandler2.getInstance().disconnect();
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

}
