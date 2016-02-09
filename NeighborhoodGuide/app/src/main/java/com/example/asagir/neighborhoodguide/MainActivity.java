package com.example.asagir.neighborhoodguide;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mRestaurantListView;
    private CursorAdapter mCursorAdapter;
    RestaurantSQLiteOpenHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mRestaurantListView = (ListView) findViewById(R.id.restaurant_listView);

        final Cursor cursor = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this).getRestaurantList();

        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{RestaurantSQLiteOpenHelper.COL_RESTAURANT_NAME}, new int[]{android.R.id.text1}, 0);
        mRestaurantListView.setAdapter(mCursorAdapter);

        mHelper = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this);

        handleIntent(getIntent());

        mRestaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                cursor.moveToPosition(position);
                intent.putExtra("_id", cursor.getInt(cursor.getColumnIndex(RestaurantSQLiteOpenHelper.COL_ID)));
                startActivity(intent);
            }

        });
    }

        @Override
        protected void onNewIntent (Intent intent){
            super.onNewIntent(intent);
            handleIntent(intent);
        }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Do The actual database search

            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, "You searched for " + query, Toast.LENGTH_SHORT).show();

            //Cursor newCursor = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this).searchRestaurantList(query);
            //Cursor helpCursor = mHelper.getRestaurantList();
            Cursor newCursor = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this).searchRestaurantList(query);
            mCursorAdapter.changeCursor(newCursor);

            mCursorAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.restaurant_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());

        searchView.setSearchableInfo(info);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Cursor newCursor = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this).searchRestaurantList(query);
                Cursor newCursor = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this).searchRestaurantList(query);
                Toast.makeText(MainActivity.this, "Search result size: " + newCursor.getCount(), Toast.LENGTH_SHORT).show();
                mCursorAdapter.swapCursor(newCursor);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }



}
