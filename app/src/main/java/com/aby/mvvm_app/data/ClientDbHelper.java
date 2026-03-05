package com.aby.mvvm_app.data;

import com.aby.mvvm_app.model.*;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

public class ClientDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "clients.db";
    private static final int DB_VERSION = 1;

    public static final String TBL = "client";
    public static final String COL_ID = "idClt";
    public static final String COL_NOM = "nomClt";
    public static final String COL_VILLE = "villeClt";

    public ClientDbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TBL + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOM + " TEXT, " +
                COL_VILLE + " TEXT)";
        db.execSQL(sql);

        // Quelques valeurs d’exemple
        ContentValues cv = new ContentValues();
        cv.put(COL_NOM, "Sara"); cv.put(COL_VILLE, "Casa"); db.insert(TBL, null, cv);
        cv.clear();
        cv.put(COL_NOM, "Yassine"); cv.put(COL_VILLE, "Rabat"); db.insert(TBL, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL);
        onCreate(db);
    }

    public long insertClient(String nom, String ville) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOM, nom);
        cv.put(COL_VILLE, ville);
        return db.insert(TBL, null, cv);
    }

    public boolean deleteClient(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL, COL_ID + "=?", new String[]{ String.valueOf(id) }) > 0;
    }
    public boolean updateClient(long id, String nom, String ville) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NOM, nom);
        cv.put(COL_VILLE, ville);

        int rows = db.update(
                TBL,
                cv,
                COL_ID + "=?",
                new String[]{ String.valueOf(id) }
        );

        return rows > 0;
    }

    public List<Client> getAllClients() {
        List<Client> out = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM client ORDER BY idClt DESC", null);
        try {
            while (c.moveToNext()) {
                long id = c.getLong(0);
                String nom = c.getString(1);
                String ville = c.getString(2);
                out.add(new Client(id, nom, ville));
            }
        } finally { c.close(); }
        return out;
    }
}