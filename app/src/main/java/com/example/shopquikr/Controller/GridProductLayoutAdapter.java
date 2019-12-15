package com.example.shopquikr.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopquikr.Model.HorizontalProductScrollModel;
import com.example.shopquikr.Activity.ProductDetailsActivity;
import com.example.shopquikr.R;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(viewGroup.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID", horizontalProductScrollModelList.get(position).getProductID());
                    viewGroup.getContext().startActivity(productDetailsIntent);
                }
            });

            // Image details set starts
            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productDescription = view.findViewById(R.id.h_s_product_description);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);
            // Image details set ends
            // Load images with glide
            Glide.with(viewGroup.getContext()).load(horizontalProductScrollModelList.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_home_24px)).into(productImage);
            productTitle.setText(horizontalProductScrollModelList.get(position).getProductTitle());
            productDescription.setText(horizontalProductScrollModelList.get(position).getProductDescription());
            productPrice.setText("$" + horizontalProductScrollModelList.get(position).getProductPrice());

        } else {
            view = convertView;
        }
        return view;
    }
}
