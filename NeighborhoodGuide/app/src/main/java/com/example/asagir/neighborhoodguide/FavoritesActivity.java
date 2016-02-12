package com.example.asagir.neighborhoodguide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FavoritesActivity extends AppCompatActivity {
    private CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ListView favoritesListView = (ListView) findViewById(R.id.favoritesListView);

        RestaurantSQLiteOpenHelper helper = RestaurantSQLiteOpenHelper.getmInstance(FavoritesActivity.this);


        final int id = getIntent().getIntExtra("_id", -1);
        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mCursorAdapter.getCursor();
                Intent intent = new Intent(FavoritesActivity.this, DetailsActivity.class);

                cursor.moveToPosition(position);
                int idLocation = cursor.getInt(cursor.getColumnIndex(RestaurantSQLiteOpenHelper.COL_ID));
                intent.putExtra("_id", idLocation);
                startActivity(intent);

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = RestaurantSQLiteOpenHelper.getmInstance(FavoritesActivity.this).getFavorites();

        if (mCursorAdapter == null) {

            mCursorAdapter = new SimpleCursorAdapter(this, R.layout.custom_layout, cursor, new String[]{RestaurantSQLiteOpenHelper.COL_RESTAURANT_NAME}, new int[]{android.R.id.text1}, 0);
            ListView favoritesListView = (ListView) findViewById(R.id.favoritesListView);
            favoritesListView.setAdapter(mCursorAdapter);
        } else {
            mCursorAdapter.changeCursor(cursor);
        }


        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


}
