package com.example.asagir.neighborhoodguide;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by asagir on 2/8/16.
 */
public class DBAssetHelper extends SQLiteAssetHelper{
    public DBAssetHelper(Context context){ super(context, "RestaurantList.db", null, 1); }
}
