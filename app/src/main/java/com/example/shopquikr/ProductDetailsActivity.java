package com.example.shopquikr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWishListBtn;

    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;

    // Rating Layout Starts
    private LinearLayout rateNowContainer;
    // Rating Layout Ends


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.products_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishListBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.image2);
        productImages.add(R.drawable.image2);
        productImages.add(R.drawable.image2);
        productImages.add(R.drawable.image2);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);

        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);
        addToWishListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
                }else {
                    ALREADY_ADDED_TO_WISHLIST = true;
                    addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // Rating Layout Starts
        rateNowContainer = findViewById(R.id.rate_now_container);
        for(int x = 0; x < rateNowContainer.getChildCount();x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(starPosition);
                }
            });
        }
        // Rating Layout Ends
    }

    private void setRating(int starPosition){
        for(int x = 0; x < rateNowContainer.getChildCount();x++){
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
