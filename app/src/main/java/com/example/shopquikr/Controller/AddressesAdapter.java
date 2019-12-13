package com.example.shopquikr.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopquikr.Model.AddressesModel;
import com.example.shopquikr.R;

import java.util.List;

import static com.example.shopquikr.Activity.DeliveryActivity.SELECT_ADDRESS;
import static com.example.shopquikr.View.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.shopquikr.Activity.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private List<AddressesModel> addressesModelList;
    private int mode;
    private int preSelectedPosition=-1;

    public AddressesAdapter(List<AddressesModel> addressesModelList, int mode) {
        this.addressesModelList = addressesModelList;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = addressesModelList.get(position).getFullname();
        String address = addressesModelList.get(position).getAddress();
        String zipcode = addressesModelList.get(position).getZipcode();
        Boolean selected = addressesModelList.get(position).getSelected();
        holder.setData(name, address, zipcode, selected, position);

    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView zipcode;
        private ImageView icon;
        private LinearLayout optionConatiner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            zipcode = itemView.findViewById(R.id.zipcode);
            icon = itemView.findViewById(R.id.icon_view);
            optionConatiner = itemView.findViewById(R.id.option_container);
        }

        private void setData(String username, String userAddress, String userZipcode, final Boolean selected, final int position) {
            fullname.setText(username);
            address.setText(userAddress);
            zipcode.setText(userZipcode);

            if (mode == SELECT_ADDRESS) {
                icon.setImageResource(R.mipmap.check);
                if (selected) {
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                } else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (preSelectedPosition != position) {
                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, position);
                            preSelectedPosition = position;
                        }
                    }
                });
            } else if (mode == MANAGE_ADDRESS) {
                optionConatiner.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.vertical_dots);
                icon.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        optionConatiner.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }
        }
    }
}
