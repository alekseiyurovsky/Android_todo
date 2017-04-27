package com.example.st57166.my_applikuha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.jar.Attributes;

public class itemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        Boolean isChecked = intent.getBooleanExtra("Checked", false);

        TextView textView = (TextView) findViewById(R.id.textView2);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        textView.setText(name);
        checkBox.setChecked(isChecked);
    }
}
