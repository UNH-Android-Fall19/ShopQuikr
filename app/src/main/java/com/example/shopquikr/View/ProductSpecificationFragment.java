package com.example.shopquikr.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopquikr.Controller.ProductSpecificationAdapter;
import com.example.shopquikr.Model.ProductSpecificationModel;
import com.example.shopquikr.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {


    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecyclerView;
    public List<ProductSpecificationModel> productSpecificationModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);
        productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);


        /*
        productSpecificationModelList.add(new ProductSpecificationModel(0,"Technical"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","2GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","2GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ROM","32GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ROM","32GB"));
*/


        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }

}