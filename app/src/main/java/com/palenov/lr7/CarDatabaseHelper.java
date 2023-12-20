package com.palenov.lr7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class CarDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "car_database";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_NAME = "cars";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_BODY_TYPE = "body_type";
    public static final String COLUMN_ENGINE_TYPE = "engine_type";
    public static final String COLUMN_TRANSMISSION_TYPE = "transmission_type";

    public CarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BRAND + " TEXT, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_BODY_TYPE + " TEXT, " +
                COLUMN_ENGINE_TYPE + " TEXT, " +
                COLUMN_TRANSMISSION_TYPE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCar(String brand, String model, String bodyType, String engineType, String transmissionType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_BODY_TYPE, bodyType);
        values.put(COLUMN_ENGINE_TYPE, engineType);
        values.put(COLUMN_TRANSMISSION_TYPE, transmissionType);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor searchCars(String bodyType, String engineType, String transmissionType) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = null;
        List<String> selectionArgs = new ArrayList<>();


        if (!TextUtils.isEmpty(bodyType)) {
            selection = COLUMN_BODY_TYPE + " = ?";
            selectionArgs.add(bodyType);
        }

        if (!TextUtils.isEmpty(engineType)) {
            if (selection != null) {
                selection += " AND ";
            } else {
                selection = "";
            }
            selection += COLUMN_ENGINE_TYPE + " = ?";
            selectionArgs.add(engineType);
        }

        if (!TextUtils.isEmpty(transmissionType)) {
            if (selection != null) {
                selection += " AND ";
            } else {
                selection = "";
            }
            selection += COLUMN_TRANSMISSION_TYPE + " = ?";
            selectionArgs.add(transmissionType);
        }

        String[] columns = {COLUMN_BRAND, COLUMN_MODEL, COLUMN_BODY_TYPE, COLUMN_ENGINE_TYPE, COLUMN_TRANSMISSION_TYPE};
        String[] argsArray = new String[selectionArgs.size()];
        argsArray = selectionArgs.toArray(argsArray);

        return db.query(TABLE_NAME, columns, selection, argsArray, null, null, null);
    }
}
