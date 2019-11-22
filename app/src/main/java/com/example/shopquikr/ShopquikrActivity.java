package com.example.shopquikr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null){
            Intent registerIntent=new Intent(ShopquikrActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        }else{
            Intent mainIntent=new Intent(ShopquikrActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
    */

}
