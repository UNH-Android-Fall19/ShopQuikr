package com.example.shopquikr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoryRecyclerView = findViewById(R.id.catergory_recyclerview);

        Log.i("recycler", "categoryRecyclerView : ");
        Log.i("recycler", "xyz : "+categoryRecyclerView.toString());

        // Banner Slider Starts
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder, "#077AE4"));
        // Banner Slider Ends

        //Horizontal Product layout starts
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.image2, "iPhone 5", "Brand New", "299$"));
        //Horizontal Product layout ends


        // Recycler View Testing Starts
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.mipmap.banner, "#000000"));
        homePageModelList.add(new HomePageModel(2, "Deals of the Day!", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3, "Deals of the Day!", horizontalProductScrollModelList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            return true;
        } else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
