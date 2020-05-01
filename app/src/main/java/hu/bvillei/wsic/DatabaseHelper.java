package hu.bvillei.wsic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Food.db";
    public static final String TABLE_NAME = "food_table";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "VEGETARIAN";
    public static final String COL_4 = "TYPE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "VEGETARIAN INTEGER NOT NULL CHECK (VEGETARIAN IN (0,1)), " +
                "TYPE TEXT NOT NULL CHECK (TYPE IN ('SOUP', 'MAIN', 'DESSERT')))");

        insertData("Hagyma", 1, "SOUP");
        insertData("Hús", 0, "SOUP");
        insertData("Minestrone", 1, "SOUP");
        insertData("Tárkonyos csirkeragu", 0, "SOUP");
        insertData("Fokhagyma", 1, "SOUP");
        insertData("Bab", 0, "SOUP");
        insertData("Cukkini", 1, "SOUP");
        insertData("Paradicsom", 1, "SOUP");
        insertData("Brokkoli", 1, "SOUP");
        insertData("Gomba", 1, "SOUP");
        insertData("Borsó", 1, "SOUP");
        insertData("Gulyás", 0, "SOUP");
        insertData("Karfiol", 1, "SOUP");
        insertData("Lencse", 1, "SOUP");
        insertData("Karalábé", 1, "SOUP");
        insertData("Gazpacho", 1, "SOUP");

        insertData("Sajtos tészta", 1, "MAIN");
        insertData("Mákos/Diós tészta", 1, "MAIN");
        insertData("Spagetti", 0, "MAIN");
        insertData("Paeya", 1, "MAIN");
        insertData("Lasagne", 0, "MAIN");
        insertData("Sztapacska", 1, "MAIN");
        insertData("Rakott krumpli", 0, "MAIN");
        insertData("Enchelada", 0, "MAIN");
        insertData("Lazac", 1, "MAIN");
        insertData("Rántotthús", 0, "MAIN");
        insertData("Puliszka", 1, "MAIN");
        insertData("Uncle Bens", 0, "MAIN");
        insertData("Grillsajt", 1, "MAIN");
        insertData("Töltött káposzta", 0, "MAIN");
        insertData("Székelykáposzta", 0, "MAIN");
        insertData("Paprikás krumpli", 0, "MAIN");
        insertData("Csirkepörkölt", 0, "MAIN");
        insertData("Gombapaprikás", 1, "MAIN");
        insertData("Tojásos galuska", 1, "MAIN");
        insertData("Túrós csusza", 1, "MAIN");
        insertData("Lencsefőzelék", 1, "MAIN");
        insertData("Fasirt", 0, "MAIN");
        insertData("Sólet", 1, "MAIN");
        insertData("Rák", 1, "MAIN");
        insertData("Oldalas", 0, "MAIN");
        insertData("Szűzérme", 0, "MAIN");
        insertData("Sajtos csirke", 0, "MAIN");
        insertData("Svéd húsgolyók", 0, "MAIN");
        insertData("Vargabéles", 1, "MAIN");
        insertData("Pizza", 1, "MAIN");
        insertData("Paradicsomos káposzta", 1, "MAIN");
        insertData("Spenót", 1, "MAIN");

        insertData("Tiramisu", 1, "DESSERT");
        insertData("Zserbó", 1, "DESSERT");
        insertData("Hókifli", 1, "DESSERT");
        insertData("Banánkenyér", 1, "DESSERT");
        insertData("Almás pite", 1, "DESSERT");
        insertData("Túrógombóc", 1, "DESSERT");
        insertData("Mákos/Diós guba", 1, "DESSERT");
        insertData("Rétes", 1, "DESSERT");
        insertData("Túrós-meggyes", 1, "DESSERT");
        insertData("Pöttyöske", 1, "DESSERT");
        insertData("Brownie", 1, "DESSERT");
        insertData("Csodadiós", 1, "DESSERT");
        insertData("Palacsinta", 1, "DESSERT");
        insertData("Muffin", 1, "DESSERT");
        insertData("Torta", 1, "DESSERT");
        insertData("Aranygaluska", 1, "DESSERT");
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

    public List<Food> getAllFoodFromDB() {
        List<Food> list = new ArrayList<>();
        Cursor res = this.getAllData();
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);
            boolean vegetarian = (res.getInt(2) == 1);
            Type type = Type.valueOf(res.getString(3));

            list.add(new Food(id, name, vegetarian, type));
        }
        return list;
    }

    public Integer deleteData(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {String.valueOf(food.getId())});
    }
}
