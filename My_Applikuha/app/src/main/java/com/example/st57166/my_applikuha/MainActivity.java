package com.example.st57166.my_applikuha;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ListElement> listy = new ArrayList<ListElement>();
    private ArrayAdapter<ListElement> adapter;
    private DbHelper db =  DbHelper.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =(EditText) findViewById(R.id.editText2);
        adapter = new ArrayAdapter<ListElement>(this, R.layout.list_itemchik, R.id.textik, listy){
            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                //return super.getView(position, convertView, parent);
                convertView = View.inflate(MainActivity.this, R.layout.list_itemchik, null);
                ((TextView)convertView.findViewById(R.id.textik)).setText(listy.get(position).GetName());
                ((CheckBox)convertView.findViewById(R.id.checkboxy)).setChecked(listy.get(position).GetCheckedStatus());
                ((CheckBox)convertView.findViewById(R.id.checkboxy)).setTag(position);
                ((Button)convertView.findViewById(R.id.delete_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listy.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                return convertView;
            }
        };
        ListView listView = (ListView) findViewById(R.id.listviewka);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent details = new Intent(MainActivity.this, itemDetails.class);
                details.putExtra("Name", listy.get(position).GetName());
                details.putExtra("Checked", listy.get(position).GetCheckedStatus());
                startActivity(details);
            }
        });
    }

    private EditText editText;

    public void add_item(View v) {

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nothing to add!", Toast.LENGTH_SHORT).show();
        }
        else {
            listy.add(new ListElement(editText.getText().toString()));
            adapter.notifyDataSetChanged();
            editText.setText("");
        }
    };

    public void checkbox_click (View v) {
        CheckBox checkbox =(CheckBox)v.findViewById(R.id.checkboxy);
        int elementID = (Integer) checkbox.getTag();
        listy.set(elementID, new ListElement(listy.get(elementID).GetName(),checkbox.isChecked()));
        adapter.notifyDataSetChanged();
    }

    public void delete_item(View v) {
        Toast.makeText(this, "DELETED!", Toast.LENGTH_SHORT).show();
    };
}

