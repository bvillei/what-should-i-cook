package hu.bvillei.wsic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Food.db";
    public static final String TABLE_NAME = "food_table";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "VEGETARIAN";
    public static final String COL_4 = "TYPE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "VEGETARIAN INTEGER NOT NULL CHECK (VEGETARIAN IN (0,1)), " +
                "TYPE TEXT NOT NULL CHECK (TYPE IN ('SOUP', 'MAIN', 'DESSERT')))");

        insertData("Pizza", 1, "MAIN");
        insertData("Gyros", 0, "MAIN");
        insertData("Chicken Soup", 0, "SOUP");
        insertData("Gazpacho", 1, "SOUP");
        insertData("Pho", 0, "SOUP");
        insertData("Tiramisu", 1, "DESSERT");
        insertData("Cake", 1, "DESSERT");
        insertData("Muffin", 1, "DESSERT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String name, int vegetarian, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, vegetarian);
        contentValues.put(COL_4, type);
        return (db.insert(TABLE_NAME, null, contentValues) != -1);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

}
