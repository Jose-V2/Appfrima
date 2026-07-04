package com.uth.appfrima;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FirmasDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SIGNATURES = "signatures";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "descripcion";
    public static final String COLUMN_SIGNATURE = "firma_digital";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SIGNATURES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_SIGNATURE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNATURES);
        onCreate(db);
    }

    public long insertSignature(String descripcion, byte[] firmaDigital) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, descripcion);
        values.put(COLUMN_SIGNATURE, firmaDigital);
        long id = db.insert(TABLE_SIGNATURES, null, values);
        db.close();
        return id;
    }

    public List<Signature> getAllSignatures() {
        List<Signature> signatures = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SIGNATURES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Signature signature = new Signature();
                signature.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                signature.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                signature.setFirmaDigital(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_SIGNATURE)));
                signatures.add(signature);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return signatures;
    }
}
