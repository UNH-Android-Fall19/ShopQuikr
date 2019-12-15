package com.example.shopquikr.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopquikr.Controller.WishListAdapter;
import com.example.shopquikr.DAO.DBQueries;
import com.example.shopquikr.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishListFragment extends Fragment {

    private RecyclerView wishListRecyclerView;
    private Button b;
    public static WishListAdapter wishListAdapter;

    public MyWishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        // Recycler view starts
        wishListRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishListRecyclerView.setLayoutManager(linearLayoutManager);

        if (DBQueries.wishListModelList.size() == 0) {
            DBQueries.wishList.clear();
            DBQueries.loadWishList(getContext(), true);
        }


        wishListAdapter = new WishListAdapter(DBQueries.wishListModelList, true);
        wishListRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();


        return view;
    }

}
