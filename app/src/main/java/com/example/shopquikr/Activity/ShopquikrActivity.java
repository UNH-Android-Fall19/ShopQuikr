package com.example.shopquikr.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.shopquikr.R;
import com.example.shopquikr.View.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ShopquikrActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopquikr);
        firebaseAuth=FirebaseAuth.getInstance();
        SystemClock.sleep(3000);
        Intent loginIntent=new Intent(ShopquikrActivity.this, RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
