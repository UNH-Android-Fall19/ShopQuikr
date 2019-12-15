package com.example.shopquikr.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.shopquikr.DAO.DBQueries;
import com.example.shopquikr.R;
import com.example.shopquikr.View.MyAccountFragment;
import com.example.shopquikr.View.MyCartFragment;
import com.example.shopquikr.View.MyOrdersFragment;
import com.example.shopquikr.View.MyRewardsFragment;
import com.example.shopquikr.View.MyWishListFragment;
import com.example.shopquikr.View.RegisterActivity;
import com.example.shopquikr.ui.home.HomeFragment;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static int ORDER_ID = 24567;
    public static Activity mainActivity;
    public static Boolean showCart = false;
    public static Fragment homeFragmentTesting = new HomeFragment();
    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;
    private ImageView noInternetConnection;
    private int currentFragment = -1;
    private NavigationView navigationView;
    private Window window;
    private Toolbar toolbar;
    public static FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        // Main layout starts
        frameLayout = findViewById(R.id.main_framelayout);
        if (showCart) {
            mainActivity = this;
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);

        }
        // Main layout ends
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (showCart) {
                    mainActivity = null;
                    showCart = false;
                    finish();
                } else {
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setTitle("Shop Quikr");
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_notification_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            return true;
        } else if (id == android.R.id.home) {
            if (showCart) {
                mainActivity = null;
                showCart = false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Navigation Bar action decides where to navigate
        if (id == R.id.nav_shop_quikr) {
            invalidateOptionsMenu();
            setFragment(homeFragmentTesting, HOME_FRAGMENT);
        } else if (id == R.id.nav_my_orders) {
            gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
        } else if (id == R.id.nav_my_rewards) {
            gotoFragment("My Rewards", new MyRewardsFragment(), REWARDS_FRAGMENT);
        } else if (id == R.id.nav_my_cart) {
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
        } else if (id == R.id.nav_my_wishlist) {
            gotoFragment("My Wishlist", new MyWishListFragment(), WISHLIST_FRAGMENT);
        } else if (id == R.id.nav_my_account) {
            gotoFragment("MyAccount", new MyAccountFragment(), ACCOUNT_FRAGMENT);
        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            DBQueries.clearData();
            Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(loginIntent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            if (fragmentNo == REWARDS_FRAGMENT) {
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}
