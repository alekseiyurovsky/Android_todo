package com.example.st57166.my_applikuha;

/**
 * Created by aleksejs.jurovskis on 4/27/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskDetails extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String theme = preferences.getString("theme", "default");
        if (theme.equals("default")) setTheme(R.style.AppTheme);
        if (theme.equals("red")) setTheme(R.style.AppTheme_Red);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final TextView detailsTextView = (TextView) findViewById(R.id.textView2);
        final CheckBox detailsCheckBox = (CheckBox) findViewById(R.id.checkBox);
        final ListElement task = (ListElement) getIntent().getSerializableExtra("Task");
        final int pos = (int) getIntent().getSerializableExtra("Position");
        detailsTextView.setText(task.GetName());
        detailsCheckBox.setChecked(task.GetCheckedStatus());

        Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, task.GetName());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "sharing"));
            }
        });

    }
}