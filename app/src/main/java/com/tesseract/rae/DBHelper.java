package com.tesseract.rae;

/**
 * Created by fernandogarcia on 29/06/14.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Recientes.db";
    public static final String RECIENTES_TABLE_NAME = "busquedas_recientes";
    public static final String RECIENTES_COLUMN_ID = "id";
    public static final String RECIENTES_COLUMN_WORD = "palabra";
    public static final String RECIENTES_COLUMN_DTYPE = "dtype";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table busquedas_recientes " +
                        "(id integer primary key, palabra text,dtype text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS busquedas_recientes");
        onCreate(db);
    }

    public boolean insertBusqueda(String palabra, String dtype)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("palabra", palabra);
        contentValues.put("dtype", dtype);

        db.insert("busquedas_recientes", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from busquedas_recientes where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECIENTES_TABLE_NAME);
        return numRows;
    }

    /*
    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }*/

    public Integer deleteBusqueda (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("busquedas_recientes",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList getAllBusquedas()
    {
        ArrayList array_list = new ArrayList();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from busquedas_recientes", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(RECIENTES_TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}

