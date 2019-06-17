package com.example.qtandroid;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

public class AskData extends AppCompatActivity {

    private int ID;
    private int position;
    private String radius = "";
    private Button button;
    private Spinner spinner;
    private EditText askRadius;
    private boolean enabled = false;
    public static final int NEW_CLUSTER = 1;
    public static final int FILE_CLUSTER = 2;

    public static void openAskData(Context context) {
        ActivityUtils.open(AskData.class, context);
    }

    public static void openAskDataWithParams(Context context, Bundle bundle) {
        ActivityUtils.openWithParams(AskData.class, context, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());

        Bundle b = getIntent().getExtras();
        ID = -1;
        if (b != null)
            ID = b.getInt("key");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_cluster);

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

        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, R.layout.color_spinner_layout);

        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        // Apply the adapter to the spinner

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                position = pos;
                enabled = position != 0 && radius.isEmpty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        askRadius = findViewById(R.id.insRadius);

        askRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                radius = askRadius.getText().toString();
                enabled = position != 0 && !radius.isEmpty();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    protected void setButton(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enabled) {
                    Toast.makeText(getApplicationContext(), "bene", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.fill, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
