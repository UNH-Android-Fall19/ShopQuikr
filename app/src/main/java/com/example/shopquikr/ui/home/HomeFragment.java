package com.example.shopquikr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopquikr.Controller.CategoryAdapter;
import com.example.shopquikr.Controller.HomePageAdapter;
import com.example.shopquikr.Model.HomePageModel;
import com.example.shopquikr.R;

import java.util.ArrayList;

import static com.example.shopquikr.DAO.DBQueries.categoryModelList;

import static com.example.shopquikr.DAO.DBQueries.lists;
import static com.example.shopquikr.DAO.DBQueries.loadCategories;
import static com.example.shopquikr.DAO.DBQueries.loadFragmentData;
import static com.example.shopquikr.DAO.DBQueries.loadedCategoriesNames;


public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecylcerView;
    private HomePageAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.catergory_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        if (categoryModelList.size() == 0){
            loadCategories(categoryAdapter,getContext());
        }else{
            categoryAdapter.notifyDataSetChanged();
        }

        // Recycler View Testing Starts
        homePageRecylcerView = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecylcerView.setLayoutManager(testingLayoutManager);

        if (lists.size() == 0){
            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(0));
            loadFragmentData(adapter,getContext(),0,"Home");
        }else{
            adapter = new HomePageAdapter(lists.get(0));
            adapter.notifyDataSetChanged();
        }


        homePageRecylcerView.setAdapter(adapter);
        // Recycler View Testing Ends

        return view;
    }

}