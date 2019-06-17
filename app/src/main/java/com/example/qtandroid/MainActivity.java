package com.example.qtandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        setTheme(ThemeUtils.defaultTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DarkSwitch = findViewById(R.id.DarkSwitch);
        DarkSwitch.setChecked(AppCompatDelegate.getDefaultNightMode()==MODE_NIGHT_YES);

        ThemeUtils.listen(MainActivity.this, DarkSwitch);

        Button buttonDetails;
        Button buttonCluster;

        buttonDetails = findViewById(R.id.details);
        setDetailsButton(buttonDetails, MainActivity.this);

        select = findViewById(R.id.select);
        buttonCluster = findViewById(R.id.esegui);
        setClusterButton(buttonCluster, MainActivity.this);

    }

    protected void setDetailsButton(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details.openDetails(context);
            }
        });
    }

    protected void setClusterButton(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isNewCluster = select.getCheckedRadioButtonId() == R.id.newcluster;
                boolean isFileCluster = select.getCheckedRadioButtonId() == R.id.filecluster;

                if (!(isNewCluster || isFileCluster)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder
                            .setMessage(R.string.noSelect)
                            .setCancelable(true)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (ConnectionUtils.absentConnection(MainActivity.this)) {
                    ConnectionUtils.openConnectionDialog(MainActivity.this, select);
                } else {
                    Bundle bundle = new Bundle();
                    if (isNewCluster) {                             // caso in cui scelgo di creare un nuovo clustering
                        bundle.putInt("key", AskData.NEW_CLUSTER);
                        AskData.openAskDataWithParams(context, bundle);
                    } else if (isFileCluster) {                      // caso in cui voglio caricare da file
                        bundle.putInt("key", AskData.FILE_CLUSTER);
                        AskData.openAskDataWithParams(context, bundle);
                    }
                }
            }
        });
    }

    public void checkRadio(View v) {
        int radioId = select.getCheckedRadioButtonId();
        selected = findViewById(radioId);
        Toast.makeText(this, "Hai selezionato: " + selected.getText(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacts:
                Contacts.openContacts(MainActivity.this);
                return true;
            case R.id.verdino:
                Contacts.openContacts(MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



