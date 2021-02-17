package com.example.app_bancaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    final String t = "CREATE TABLE cliente (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, identificacion TEXT, correo TEXT, contrasena TEXT, cuenta TEXT, monto DOUBLE, estado TEXT)";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(t);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
