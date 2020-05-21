package com.mora.gonzalez.javier.softcuatro.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PagosClientes extends SQLiteOpenHelper {
    private String sqlPagosPayPal = "CREATE TABLE PagosPayPal (_id INTEGER PRIMARY KEY AUTOINCREMENT, correo TEXT, nombrecompleto TEXT, monto TEXT)";
    private String sqlPagosDirectos = "CREATE TABLE PagosBanco (_id INTEGER PRIMARY KEY AUTOINCREMENT, numcuenta TEXT, nombrecompleto TEXT, monto TEXT)";
    public PagosClientes(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlPagosPayPal);
        db.execSQL(sqlPagosDirectos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PagosPayPal");
        db.execSQL("DROP TABLE IF EXISTS PagosBanco");
        db.execSQL(sqlPagosPayPal);
        db.execSQL(sqlPagosDirectos);
    }
}
