package com.example.ahorros.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "ahorros.db";
    public static final String TABLE_AHORROS = "ahorros";
    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NOMBRE,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_AHORROS+"(" +
                "id integer primary key autoincrement," +
                "fecha text," +
                "item text," +
                "precio real," +
                "categoria text," +
                "detalles text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE " + TABLE_AHORROS);
    onCreate(db);
    }
}
