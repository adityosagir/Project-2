package com.example.asagir.neighborhoodguide;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

        mHelper = RestaurantSQLiteOpenHelper.getmInstance(MainActivity.this);

        final Cursor cursor = mHelper.getRestaurantList();
        final Button button = (Button) findViewById(R.id.button);

        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{RestaurantSQLiteOpenHelper.COL_RESTAURANT_NAME}, new int[]{android.R.id.text1}, 0);
        mRestaurantListView.setAdapter(mCursorAdapter);


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

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(i);
                //finish();
            }
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

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

    private void actionBarSetup(String title){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

//    private int getDrawableValue(String icon){
//        switch(icon){
//            case "search":
//                return android.R.drawable.ic_menu_search;
//            case "add":
//                return android.R.drawable.ic_menu_add;
//            case "upload":
//                return android.R.drawable.ic_menu_upload;
//            case "play":
//                return android.R.drawable.ic_media_play;
//            default:
//                return 0;
//        }

}