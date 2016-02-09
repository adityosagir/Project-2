package com.example.asagir.neighborhoodguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asagir.neighborhoodguide.R;
import com.example.asagir.neighborhoodguide.RestaurantSQLiteOpenHelper;

/**
 * Created by asagir on 2/5/16.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        int id = getIntent().getIntExtra("_id", -1);

        if (id >= 0) {
            TextView restaurantName = (TextView) findViewById(R.id.name);
            TextView restaurantCuisine = (TextView) findViewById(R.id.cuisine);
            TextView restaurantAddress = (TextView) findViewById(R.id.address);
            TextView restaurantDescription = (TextView) findViewById(R.id.description);

            String name = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getNameById(id);
            String cuisine = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getCuisineById(id);
            String address = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getAddressById(id);
            String description = RestaurantSQLiteOpenHelper.getmInstance(DetailsActivity.this).getDescriptionById(id);

            restaurantName.setText(name);
            restaurantCuisine.setText(cuisine);
            restaurantAddress.setText(address);
            restaurantDescription.setText(description);

        }

    }
}
