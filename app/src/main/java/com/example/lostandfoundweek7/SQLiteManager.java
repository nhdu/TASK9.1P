package com.example.lostandfoundweek7;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "LostFoundDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "LOSTANDFOUND";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String PHONE_FIELD = "phone";
    private static final String DESCRIPTION_FIELD = "desc";
    private static final String DATE_FIELD = "date";
    private static final String LOCATION_FIELD = "location";
    private static final String POST_TYPE = "type";

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if (sqLiteManager == null)
        {
            sqLiteManager = new SQLiteManager(context);
        }

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INT NOT NULL PRIMARY KEY, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(PHONE_FIELD)
                .append(" TEXT, ")
                .append(DESCRIPTION_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" TEXT, ")
                .append(LOCATION_FIELD)
                .append(" TEXT, ")
                .append(POST_TYPE)
                .append(" TEXT)");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addItemToDatabase(ItemData item)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, item.getId());
        contentValues.put(NAME_FIELD, item.getName());
        contentValues.put(PHONE_FIELD, item.getPhone());
        contentValues.put(DESCRIPTION_FIELD, item.getDescription());
        contentValues.put(DATE_FIELD, item.getDate());
        contentValues.put(LOCATION_FIELD, item.getLocation());
        contentValues.put(POST_TYPE, item.getType());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateItemListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try(Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if (result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(0);
                    String name = result.getString(1);
                    String phone = result.getString(2);
                    String description = result.getString(3);
                    String date = result.getString(4);
                    String location = result.getString(5);
                    String type = result.getString(6);
                    ItemData item = new ItemData(name, phone, description, date, location, type, id);
                    if (!ItemData.itemList.contains(item))
                    {
                        ItemData.itemList.add(item);
                    }
                }
            }
        }

    }

    public int deleteItem(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String [] {id});
    }

}

