package com.example.shopquikr.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shopquikr.Controller.CartAdapter;
import com.example.shopquikr.DAO.DBQueries;
import com.example.shopquikr.Model.CartItemModel;
import com.example.shopquikr.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddNewAddressBtn;
    public static final int SELECT_ADDRESS = 0;
    private Button continueBtn;
    private TextView totalAmount;
    private ConstraintLayout orderConfirmationLayout;
    private ImageButton continueShoppingBtn;
    private TextView orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        continueBtn = findViewById(R.id.cart_continue_btn);
        totalAmount = findViewById(R.id.total_cart_amount);
        orderConfirmationLayout = findViewById(R.id.order_confirmation_layout);
        continueShoppingBtn = findViewById(R.id.continue_shopping_btn);
        orderId = findViewById(R.id.order_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        // Recycler starts
        deliveryRecyclerView = (RecyclerView) findViewById(R.id.delivery_recyclerview);

        changeOrAddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        orderId.setText("Order ID : " + MainActivity.ORDER_ID++);
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList, totalAmount);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        // Recycler ends
        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);

        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // Upon clicking continue the order is confirmed
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mainActivity != null) {
                    MainActivity.mainActivity.finish();
                    MainActivity.mainActivity = null;
                    MainActivity.showCart = false;
                }
                if (ProductDetailsActivity.productDetailsActivity != null) {
                    ProductDetailsActivity.productDetailsActivity.finish();
                    ProductDetailsActivity.productDetailsActivity = null;
                }

                orderId.setText("Order ID : " + MainActivity.ORDER_ID++);
                orderConfirmationLayout.setVisibility(View.VISIBLE);


            }
        });
        // Upon clicking continueShoppingButton, navigated to Home screen
        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.mainActivity != null) {
                    MainActivity.mainActivity.finish();
                    MainActivity.mainActivity = null;
                    MainActivity.showCart = false;
                }
                if (ProductDetailsActivity.productDetailsActivity != null) {
                    ProductDetailsActivity.productDetailsActivity.finish();
                    ProductDetailsActivity.productDetailsActivity = null;
                }

                orderId.setText("Order ID : " + MainActivity.ORDER_ID++);
                orderConfirmationLayout.setVisibility(View.VISIBLE);
                DBQueries.clearData();
                Intent homeIntent = new Intent(DeliveryActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
