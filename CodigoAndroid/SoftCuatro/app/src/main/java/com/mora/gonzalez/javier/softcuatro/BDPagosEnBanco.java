package com.mora.gonzalez.javier.softcuatro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDPagosEnBanco extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE PagosBanco (_id INTEGER PRIMARY KEY AUTOINCREMENT, numcuenta TEXT, nombrecompleto TEXT, monto TEXT)";
    public BDPagosEnBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PagosBanco");
        db.execSQL(sqlCreate);
    }
}
