package com.example.st57166.my_applikuha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private DbHelper db = DbHelper.getInstance(this);
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String theme = preferences.getString("theme", "default");
        if (theme.equals("default")) setTheme(R.style.AppTheme);
        if (theme.equals("red")) setTheme(R.style.AppTheme_Red);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =(EditText) findViewById(R.id.editText2);
        adapter = new ArrayAdapter<ListElement>(this, R.layout.list_itemchik, R.id.textik, listy){
            @NonNull
            @Override

            public View getView(final int position, View convertView, ViewGroup parent) {
                convertView = View.inflate(MainActivity.this, R.layout.list_itemchik, null);
                ((TextView)convertView.findViewById(R.id.textik)).setText(listy.get(position).GetName());
                ((CheckBox)convertView.findViewById(R.id.checkboxy)).setChecked(listy.get(position).GetCheckedStatus());
                ((CheckBox)convertView.findViewById(R.id.checkboxy)).setTag(position);
                ((CheckBox)convertView.findViewById(R.id.checkboxy)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListElement updatingListElement = listy.get(position);
                        if (updatingListElement.GetCheckedStatus()) {
                            updatingListElement.SetCheckedStatus(false);
                        }
                        else {
                            updatingListElement.SetCheckedStatus(true);
                        }
                        db.save(updatingListElement);
                        listy.clear();
                        listy.addAll(db.listAll());
                        adapter.notifyDataSetChanged();
                    }
                });
                ((Button)convertView.findViewById(R.id.delete_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int posToDelete = listy.get(position).GetId();
                        db.delete(posToDelete);
                        listy.clear();
                        listy.addAll(db.listAll());
                        adapter.notifyDataSetChanged();
                    }
                });

                final Context context = adapter.getContext();
                convertView.setClickable(true);
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TaskDetails.class);
                        intent.putExtra("Task", getItem(position));
                        intent.putExtra("Position", position);
                        context.startActivity(intent);
                    }
                });

                return convertView;
            }
        };
        ListView listView = (ListView) findViewById(R.id.listviewka);
        listView.setAdapter(adapter);
        listy.addAll(db.listAll());
        adapter.notifyDataSetChanged();
    }

    private EditText editText;

    public void add_item(View v) {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nothing to add!", Toast.LENGTH_SHORT).show();
        }
        else {
            ListElement savingListElement = new ListElement();
            savingListElement.SetName(editText.getText().toString());
            db.save(savingListElement);
            listy.clear();
            listy.addAll(db.listAll());
            adapter.notifyDataSetChanged();
            editText.setText("");
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        listy.clear();
        listy.addAll(db.listAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        switch (item.getItemId()){
            case R.id.action_default:
                editor.putString("theme","default");
                editor.commit();
                this.setTheme(R.style.AppTheme);
                this.finish();
                this.startActivity(new Intent(this, this.getClass()));
                return true;
            case R.id.action_red:
                editor.putString("theme","red");
                editor.commit();
                this.setTheme(R.style.AppTheme_Red);
                this.finish();
                this.startActivity(new Intent(this, this.getClass()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

