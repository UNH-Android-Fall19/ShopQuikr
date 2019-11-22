package com.example.shopquikr.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.shopquikr.CategoryAdapter;
import com.example.shopquikr.CategoryModel;
import com.example.shopquikr.GridProductLayoutAdapter;
import com.example.shopquikr.HomePageAdapter;
import com.example.shopquikr.HomePageModel;
import com.example.shopquikr.HorizontalProductScrollAdapter;
import com.example.shopquikr.HorizontalProductScrollModel;
import com.example.shopquikr.R;
import com.example.shopquikr.SliderAdapter;
import com.example.shopquikr.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.catergory_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        final List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Electronics"));
        categoryModelList.add(new CategoryModel("link", "Appliances"));
        categoryModelList.add(new CategoryModel("link", "Furniture"));
        categoryModelList.add(new CategoryModel("link", "Fashion"));
        categoryModelList.add(new CategoryModel("link", "Toys"));
        categoryModelList.add(new CategoryModel("link", "Arts"));
        categoryModelList.add(new CategoryModel("link", "Books"));
        categoryModelList.add(new CategoryModel("link", "Shoes"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        // Banner Slider Starts

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.image2, "#077AE4"));

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
        testing = view.findViewById(R.id.home_page_recyclerview);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.mipmap.banner, "#000000"));
        homePageModelList.add(new HomePageModel(2, "Deals of the Day!", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3, "Deals of the Day!", horizontalProductScrollModelList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // Recycler View Testing Ends

        return view;
    }

}