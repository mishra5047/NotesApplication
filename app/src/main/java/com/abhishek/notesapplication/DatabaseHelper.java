package com.abhishek.notesapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "user_notes";
    public static final String COLUMN_TITLE = "_title";
    public static final String COLUMN_DETAIL= "notes_details";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " ( " + COLUMN_TITLE + " TEXT PRIMARY KEY , " +
                COLUMN_DETAIL + " TEXT )";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItem(String title,String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DETAIL, details);

        long result =  db.insert(TABLE_NAME, null, cv);
        if (result == -1){

        }else{

        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_title=?", new String[]{row_id});

        if (result == -1) {

        }
        else{

        }
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

    public void updateNote(String oldTitle, String title, String description){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(COLUMN_TITLE, title);
        content.put(COLUMN_DETAIL, description);

        long result = db.update(TABLE_NAME, content, "_title=?", new String[]{oldTitle});

        if (result == -1){

        }else{

        }
    }

    public long noItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }

}
