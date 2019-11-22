package com.example.shopquikr;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishListFragment extends Fragment {

    private RecyclerView wishListRecyclerView;

    public MyWishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        wishListRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishListRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishListModel> wishListModelList = new ArrayList<>();
        wishListModelList.add(new WishListModel(R.drawable.product_image, "Pixel 2","3",145, "299$", "399$"));
        wishListModelList.add(new WishListModel(R.drawable.product_image, "Pixel 2","4",145, "299$", "399$"));
        wishListModelList.add(new WishListModel(R.drawable.product_image, "Pixel 2","1",145, "299$", "399$"));
        wishListModelList.add(new WishListModel(R.drawable.product_image, "Pixel 2","2",145, "299$", "399$"));
        wishListModelList.add(new WishListModel(R.drawable.product_image, "Pixel 2","3",145, "299$", "399$"));

        WishListAdapter wishListAdapter = new WishListAdapter(wishListModelList);
        wishListRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();
        return view;
    }

}
