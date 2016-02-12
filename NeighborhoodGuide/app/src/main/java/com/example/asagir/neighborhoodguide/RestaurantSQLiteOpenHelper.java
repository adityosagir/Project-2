package com.example.asagir.neighborhoodguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by asagir on 2/6/16.
 */
public class RestaurantSQLiteOpenHelper extends SQLiteOpenHelper {
    //private static final String TAG = RestaurantSQLiteOpenHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RestaurantList.db";
    public static final String RESTAURANT_LIST_TABLE = "RestaurantDetails";

    public static final String COL_ID = "_id";
    public static final String COL_RESTAURANT_NAME = "Name";
    public static final String COL_CUISINE = "Cuisine";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_FAVORITE = "Favorite";
    public static final String COL_DESCRIPTION = "Description";

    private final Context mHelperContext;

    public static final String[] RESTAURANT_COLUMNS = {COL_ID, COL_RESTAURANT_NAME, COL_CUISINE, COL_ADDRESS, COL_FAVORITE, COL_DESCRIPTION};

    private static final String CREATE_RESTAURANT_LIST_TABLE =
            "CREATE TABLE " + RESTAURANT_LIST_TABLE +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_RESTAURANT_NAME + " TEXT, " +
                    COL_CUISINE + " TEXT, " +
                    COL_ADDRESS + " TEXT, " +
                    COL_FAVORITE + " TEXT, " +
                    COL_DESCRIPTION + " TEXT) ";

    private static RestaurantSQLiteOpenHelper mInstance;

    public static RestaurantSQLiteOpenHelper getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RestaurantSQLiteOpenHelper(context.getApplicationContext());
        }

        return mInstance;
    }

    public RestaurantSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mHelperContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESTAURANT_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RESTAURANT_LIST_TABLE);
        this.onCreate(db);
    }

    public Cursor getRestaurantList() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RESTAURANT_LIST_TABLE, // a. table
                RESTAURANT_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursor;
    }

    public Cursor searchRestaurantList(String query) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorSearch = db.query(RESTAURANT_LIST_TABLE, // a. table
                RESTAURANT_COLUMNS, // b. column names
                COL_RESTAURANT_NAME + " LIKE ? OR " + COL_CUISINE + " LIKE ? OR " + COL_ADDRESS + " LIKE ?", // c. selections
                new String[]{"%" + query + "%", "%" + query + "%", "%" + query + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursorSearch;
    }

    public String getNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorName = db.query(RESTAURANT_LIST_TABLE, // a. table
                new String[]{COL_RESTAURANT_NAME}, // b. column names
                COL_ID + " = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursorName.moveToFirst()) {
            return cursorName.getString(cursorName.getColumnIndex(COL_RESTAURANT_NAME));
        } else {
            return "Name not found";
        }
    }

    public String getCuisineById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCuisine = db.query(RESTAURANT_LIST_TABLE,// a. table
                new String[]{COL_CUISINE}, // b. column names
                COL_ID + " = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursorCuisine.moveToFirst()) {
            return cursorCuisine.getString(cursorCuisine.getColumnIndex(COL_CUISINE));
        } else {
            return "Cuisine not found";
        }
    }

    public String getAddressById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorAddress = db.query(RESTAURANT_LIST_TABLE, // a. table
                new String[]{COL_ADDRESS}, // b. column names
                COL_ID + " = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursorAddress.moveToFirst()) {
            return cursorAddress.getString(cursorAddress.getColumnIndex(COL_ADDRESS));
        } else {
            return "Address not found";
        }
    }

    public Cursor getFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorFavorite = db.query(RESTAURANT_LIST_TABLE, // a. table
                RESTAURANT_COLUMNS, // b. column names
                COL_FAVORITE + " = 1", // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursorFavorite;
    }

    public int checkFavorite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorFavoriteCheck = db.query(RESTAURANT_LIST_TABLE, // a. table
                new String[]{COL_FAVORITE}, // b. column names
                COL_ID + " LIKE ? AND " + COL_FAVORITE + " = 1", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursorFavoriteCheck.moveToFirst()) {
            return cursorFavoriteCheck.getInt(cursorFavoriteCheck.getColumnIndex(COL_FAVORITE));
        }
        return -1;
    }

    public String getDescriptionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorDescription = db.query(RESTAURANT_LIST_TABLE, // a. table
                new String[]{COL_DESCRIPTION}, // b. column names
                COL_ID + " = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursorDescription.moveToFirst()) {
            return cursorDescription.getString(cursorDescription.getColumnIndex(COL_DESCRIPTION));
        } else {
            return "Description not found";
        }
    }


}