package danielstaal.pset3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Daniel Staal on 4/25/2016.
 *
 * Class to manage the sql database for the items
 */
public class DBhelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "firstdb.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE = "items";

    private String item_id = "item";

    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * create the table
     */
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    /*
     * update the table onUpgrade
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /*
     * add item to the table
     */
    public void create(String itemText){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(item_id, itemText);
        db.insert(TABLE, null, values);
    }

    /*
     * read the table in as an arraylist
     */
    public ArrayList<String> read(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + item_id + " FROM " + TABLE;
        ArrayList<String> itemList = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                itemList.add(cursor.getString(cursor.getColumnIndex(item_id)));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    /*
     * update an element in the table
     */
    public void update(String item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(item_id, item);

        db.update(TABLE, values, "_id = ? ", new String[]{String.valueOf(item)});
        db.close();
    }

    /*
     * delete an element from the table
     */
    public void delete(String item){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, item_id + " = ?", new String[]{item});
    }
}










