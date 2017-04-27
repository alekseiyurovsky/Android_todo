package com.example.st57166.my_applikuha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;




public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "TodoList.db";
    private static final String DATABASE_TABLE = "tasks";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY = "id";
    public static final String NAME = "name";
    public static final String STATUS = "checked";


    private static DbHelper INSTANCE;
    public static DbHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DbHelper(context, null, null, 1);
        }
        return  INSTANCE;
    }

    private static final String INITIAL_QUERY = "create table " +
            DATABASE_TABLE + " ("+
            KEY + " integer primary key autoincrement," +
            NAME + " text not null default '', " +
            STATUS +" boolean)";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INITIAL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS" + DATABASE_TABLE);
        onCreate(db);
    }

    public void save(ListElement task){
        ContentValues newRow = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if (task.GetId() == 0){
            newRow.put(NAME, task.GetName());
            newRow.put(STATUS, task.GetCheckedStatus());
            db.insert(DATABASE_TABLE, null, newRow);
        }
        else {
            newRow.put(NAME, task.GetName());
            newRow.put(STATUS, task.GetCheckedStatus());
            String where = KEY + "=" + task.GetId();
            String args[] = null;
            db.update(DATABASE_TABLE, newRow, where, args);
        }
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY + "=" + id;
        String args[] = null;
        db.delete(DATABASE_TABLE, where, args);
    }

    public List<ListElement> listAll(){
        List<ListElement> taskList = new LinkedList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + DATABASE_TABLE, new String[]{});
        int keyIndex = cursor.getColumnIndexOrThrow(KEY);
        int taskName = cursor.getColumnIndexOrThrow(NAME);
        int taskStatus = cursor.getColumnIndexOrThrow(STATUS);
        while (cursor.moveToNext()){
            taskList.add(new ListElement(
                    cursor.getInt(keyIndex),
                    cursor.getString(taskName),
                    cursor.getInt(taskStatus) == 1 ? true : false
            ));
        }
        return taskList;
    }
}
