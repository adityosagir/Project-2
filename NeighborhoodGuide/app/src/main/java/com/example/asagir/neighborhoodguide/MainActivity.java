package com.example.asagir.neighborhoodguide;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mRestaurantListView;
    private CursorAdapter mCursorAdapter;
    RestaurantSQLiteOpenHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        // Create a listView to display results from the database
        mRestaurantListView = (ListView) findViewById(R.id.restaurant_listView);

        mHelper = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this);

        // Create a cursor that can get information from the database
        final Cursor cursor = mHelper.getRestaurantList();

        mCursorAdapter = new SimpleCursorAdapter(this, R.layout.custom_layout, cursor, new String[]{RestaurantSQLiteOpenHelper.COL_RESTAURANT_NAME}, new int[]{android.R.id.text1}, 0){
            // Getting the data from the cursor adapter and putting it in the cutom layout view
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/geosanslight.ttf");

                textView.setTypeface(typefaceBold);
            }
        };
        mRestaurantListView.setAdapter(mCursorAdapter);

        // Search and display the results
        handleIntent(getIntent());

        mRestaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mCursorAdapter.getCursor();
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                cursor.moveToPosition(position);
                int idLocation = cursor.getInt(cursor.getColumnIndex(RestaurantSQLiteOpenHelper.COL_ID));
                intent.putExtra("_id", idLocation);
                startActivity(intent);

            }

        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    // Create a cursor and adapter to
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Do The actual database search

            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, "You searched for " + query, Toast.LENGTH_SHORT).show();

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
                Cursor textCursor = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this).searchRestaurantList(query);
                Toast.makeText(MainActivity.this, "Results matching criteria: " + textCursor.getCount(), Toast.LENGTH_SHORT).show();
                mCursorAdapter.swapCursor(textCursor);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.buttonFavoriteList) {
            Intent i = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(i);
        }
        return true;
    }


    // When you click back from the search it should go to the mainactivity screen
    @Override
    public void onBackPressed() {
        Cursor cursor = mHelper.getRestaurantList();
        mCursorAdapter.swapCursor(cursor);

    }
}