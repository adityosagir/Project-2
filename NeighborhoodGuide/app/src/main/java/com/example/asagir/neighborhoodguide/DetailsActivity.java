package com.example.asagir.neighborhoodguide;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
            ImageView restaurantTopImage = (ImageView) findViewById(R.id.detailsViewTopImage);
            ImageView restaurantLeftImage = (ImageView) findViewById(R.id.detailsViewLeftImage);
            ImageView restaurantRightImage = (ImageView) findViewById(R.id.detailsViewRightImage);


            String name = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getNameById(id);
            String cuisine = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getCuisineById(id);
            String address = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getAddressById(id);
            String description = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getDescriptionById(id);


            restaurantName.setText(name);
            restaurantCuisine.setText(cuisine);
            restaurantAddress.setText(address);
            restaurantDescription.setText(description);

            switch (id) {
                case 1:
//                    restaurantTopImage.setImageBitmap(
//                            decodeSampledBitmapFromResource(getResources(), R.drawable.crescentfood2, 100, 100));
                    restaurantTopImage.setImageResource(R.drawable.crescentfood);
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.crescentoutdoors, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.crescentfood2, 100, 100));
                    break;
                case 2:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.muramenfood2, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.muramenfood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.murameninside, 100, 100));
                    break;
                case 3:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.mwellslogo, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.mwellsfood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.mwellsinside, 100, 100));
                    break;
                case 4:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.kavalalogo, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.kavalafood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.kavalainside, 100, 100));
                    break;
                case 5:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.houseofthailogo, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.houseofthaifood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.houseofthaiinside, 100, 100));
                    break;
                case 6:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.tasteofbengaloutdoors, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.tasteofbengalfood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.tasteofbengalfood2, 100, 100));
                    break;
                case 7:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.malaguetalogo, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.malaguetafood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.malaguetainside, 100, 100));
                    break;
                case 8:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.goodtasteoutside, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.goodtastefood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.goodtastefood2, 100, 100));
                    break;
                case 9:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.manettaoutside, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.manettafood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.manettainside, 100, 100));
                    break;
                case 10:
                    restaurantTopImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.casaenriqueteam, 100, 100));
                    restaurantRightImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.casaenriquefood, 100, 100));
                    restaurantLeftImage.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(), R.drawable.casaenriquefood2, 100, 100));
                    break;
            }

            actionBarSetup(name);

            final FloatingActionButton buttonFav = (FloatingActionButton) findViewById(R.id.buttonMakeFavorite);

            if (mHelper.checkFavorite(id) == 1) {
                mFavFlag = true;
                buttonFav.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43A047")));
            } else {
                mFavFlag = false;
                buttonFav.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#40BFFF")));
            }


            buttonFav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (mFavFlag == true) { // If the button is already a favorite
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        db.execSQL("UPDATE RestaurantDetails SET Favorite = 0 WHERE _id = " + id);
                        Toast.makeText(DetailsActivity.this, mHelper.getNameById(id) + " was removed", Toast.LENGTH_SHORT).show();
                        buttonFav.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#40BFFF")));
                        mFavFlag = false;
                    } else { // If the button is not already a favorite
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        db.execSQL("UPDATE RestaurantDetails SET Favorite = 1 WHERE _id = " + id);
                        Toast.makeText(DetailsActivity.this, mHelper.getNameById(id) + " was added", Toast.LENGTH_SHORT).show();
                        buttonFav.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43A047")));
                        mFavFlag = true;
                    }

                }
            });

        }


    }

    private void actionBarSetup(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }


}
