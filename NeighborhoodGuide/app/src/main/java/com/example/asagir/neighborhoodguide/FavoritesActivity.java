package com.example.asagir.neighborhoodguide;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ListView favoritesListView = (ListView) findViewById(R.id.favoritesListView);

        RestaurantSQLiteOpenHelper helper = RestaurantSQLiteOpenHelper.getmInstance(FavoritesActivity.this);

        final Cursor cursor = helper.getFavorite();

        CursorAdapter mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{RestaurantSQLiteOpenHelper.COL_FAVORITE}, new int[]{android.R.id.text1}, 0);
        favoritesListView.setAdapter(mCursorAdapter);

        }
    }
