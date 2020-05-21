package com.mora.gonzalez.javier.softcuatro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDPagosPayPal extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE PagosPayPal (_id INTEGER PRIMARY KEY AUTOINCREMENT, correo TEXT, nombrecompleto TEXT, monto TEXT)";
    public BDPagosPayPal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PagosPayPal");
        db.execSQL(sqlCreate);
    }
}
