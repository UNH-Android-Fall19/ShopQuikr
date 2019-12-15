package com.example.shopquikr.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopquikr.Controller.MyRewardsAdapter;
import com.example.shopquikr.Model.RewardsModel;
import com.example.shopquikr.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardsFragment extends Fragment {

    private RecyclerView rewardsRecyclerView;

    public MyRewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        // Recycler view starts
        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);
        List<RewardsModel> rewardsModelList = new ArrayList<>();
        rewardsModelList.add(new RewardsModel("Cashback", "till 2nd Nov 2019", "GET 20% cashback on any product above $200 and below $2000"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardsModelList);
        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();
        return view;
    }

}
