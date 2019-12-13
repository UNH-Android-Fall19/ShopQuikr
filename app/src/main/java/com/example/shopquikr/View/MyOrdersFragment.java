package com.example.shopquikr.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopquikr.Controller.MyOrderAdapter;
import com.example.shopquikr.Model.MyOrderItemModel;
import com.example.shopquikr.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {

    private RecyclerView myOrdersRecyclerView;
    Button b;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.product_image,3,"Pixel 2XL(Black)"));
        //myOrderItemModelList.add(new MyOrderItemModel(R.drawable.image2,1,"Pixel 2XL(Black)"));
        //myOrderItemModelList.add(new MyOrderItemModel(R.drawable.product_image,2,"Pixel 2XL(Black)"));
        //myOrderItemModelList.add(new MyOrderItemModel(R.drawable.image2,4,"Pixel 2XL(Black)"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();






        return view;
    }

}
