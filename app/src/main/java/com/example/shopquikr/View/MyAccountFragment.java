package com.example.shopquikr.View;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopquikr.Activity.MyAddressesActivity;
import com.example.shopquikr.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    public static final int MANAGE_ADDRESS = 1;
    private Button viewAllAddressBtn;


    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        viewAllAddressBtn = view.findViewById(R.id.view_all_addresses_btn);
        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent = new Intent(getContext(), MyAddressesActivity.class);
                myAddressesIntent.putExtra("mode", MANAGE_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });
        return view;
    }

}
