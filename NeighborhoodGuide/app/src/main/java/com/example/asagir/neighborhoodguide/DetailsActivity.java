package com.example.asagir.neighborhoodguide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asagir.neighborhoodguide.R;
import com.example.asagir.neighborhoodguide.RestaurantSQLiteOpenHelper;

/**
 * Created by asagir on 2/5/16.
 */
public class DetailsActivity extends AppCompatActivity {
    RestaurantSQLiteOpenHelper mHelper;
    private boolean mFavFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        mHelper = new RestaurantSQLiteOpenHelper(DetailsActivity.this);
        Cursor cursor = mHelper.getRestaurantList();

        final int id = getIntent().getIntExtra("_id", -1);

        if (id >= 0) {
            TextView restaurantName = (TextView) findViewById(R.id.name);
            TextView restaurantCuisine = (TextView) findViewById(R.id.cuisine);
            TextView restaurantAddress = (TextView) findViewById(R.id.address);
            TextView restaurantDescription = (TextView) findViewById(R.id.description);
//            ImageView restaurantImage = (ImageView) findViewById(R.id.imageView);

            String name = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getNameById(id);
            String cuisine = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getCuisineById(id);
            String address = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getAddressById(id);
            String description = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getDescriptionById(id);


            restaurantName.setText(name);
            restaurantCuisine.setText(cuisine);
            restaurantAddress.setText(address);
            restaurantDescription.setText(description);
//            restaurantImage.setImageDrawable(image);

            actionBarSetup(name);

            final Button buttonFav = (Button) findViewById(R.id.buttonMakeFavorite);

            if (mHelper.checkFavorite(id) == 1) {
                mFavFlag = true;
                buttonFav.setBackgroundColor(Color.GREEN);
            } else {
                mFavFlag = false;
                buttonFav.setBackgroundColor(Color.LTGRAY);
            }


            buttonFav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (mFavFlag == true) { // If the button is already a favorite
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        db.execSQL("UPDATE RestaurantDetails SET Favorite = 0 WHERE _id = " + id);
                        Toast.makeText(DetailsActivity.this, mHelper.getNameById(id)+ " was removed", Toast.LENGTH_SHORT ).show();
                        buttonFav.setBackgroundColor(Color.LTGRAY);
                        //buttonFav.setText("Add Favorite");
                        mFavFlag = false;
                    } else { // If the button is not already a favorite
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        db.execSQL("UPDATE RestaurantDetails SET Favorite = 1 WHERE _id = " + id);
                        Toast.makeText(DetailsActivity.this, mHelper.getNameById(id) + " was added", Toast.LENGTH_SHORT ).show();
                        buttonFav.setBackgroundColor(Color.GREEN);
                        //buttonFav.setText("Favorite");
                        mFavFlag = true;
                    }

                }
            });

        }


    }

    private void actionBarSetup(String title){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

}