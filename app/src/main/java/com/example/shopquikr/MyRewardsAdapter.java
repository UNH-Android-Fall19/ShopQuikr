package com.example.shopquikr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.ViewHolder> {

    private List<RewardsModel> rewardsModelList;

    public MyRewardsAdapter(List<RewardsModel> rewardsModelList) {
        this.rewardsModelList = rewardsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String title = rewardsModelList.get(position).getTitle();
        String date = rewardsModelList.get(position).getExpiryDate();
        String body = rewardsModelList.get(position).getCouponBody();
        viewHolder.setData(title, date, body);
    }

    @Override
    public int getItemCount() {
        return rewardsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView couponTitle;
        private TextView couponExpiryDate;
        private TextView couponBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            couponTitle = itemView.findViewById(R.id.coupon_title);
            couponExpiryDate = itemView.findViewById(R.id.coupon_validity);
            couponBody = itemView.findViewById(R.id.coupon_body);

        }
        private void setData(String title, String date, String body){
            couponTitle.setText(title);
            couponExpiryDate.setText(date);
            couponBody.setText(body);
        }
    }
}
