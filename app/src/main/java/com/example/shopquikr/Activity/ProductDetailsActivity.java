package com.example.shopquikr.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopquikr.Controller.ProductDetailsAdapter;
import com.example.shopquikr.Controller.ProductImagesAdapter;
import com.example.shopquikr.DAO.DBQueries;
import com.example.shopquikr.Model.CartItemModel;
import com.example.shopquikr.Model.ProductSpecificationModel;
import com.example.shopquikr.Model.WishListModel;
import com.example.shopquikr.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shopquikr.Activity.MainActivity.currentUser;
import static com.example.shopquikr.Activity.MainActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;
    public static Activity productDetailsActivity;
    private ViewPager productImagesViewPager;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private TabLayout viewpagerIndicator;
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private TextView productOnlyDescriptionBody;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;

    // Rating Layout Starts
    public static int initialRating;
    public static LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;
    // Rating Layout Ends

    private Button buyNowBtn;
    private LinearLayout addToCartBtn;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CART = false;
    public static FloatingActionButton addToWishListBtn;
    private FirebaseFirestore firebaseFirestore;
    public static String productID;
    private DocumentSnapshot documentSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productImagesViewPager = findViewById(R.id.products_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishListBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_ratings_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating = findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        initialRating = -1;
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
                    for (long x = 1; x < ((long) (documentSnapshot.get("no_of_product_images"))) + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ") ratings");
                    productPrice.setText("$" + documentSnapshot.get("product_price").toString());
                    cuttedPrice.setText("$" + documentSnapshot.get("cutted_price").toString());
                    // Product Description Starts
                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabsContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();

                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(),
                                        documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()
                                ));
                            }
                        }

                    } else {
                        productDetailsTabsContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }
                    // Product Description Ends

                    // Rating Layout Starts
                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + " ratings");
                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_star")));
                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                    }
                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));
                    // Rating Layout Ends
                    if (DBQueries.wishList.size() == 0) {
                        DBQueries.loadWishList(ProductDetailsActivity.this, false);
                    }
                    if (DBQueries.myRating.size() == 0) {
                        DBQueries.loadRatingList(ProductDetailsActivity.this);
                    }
                    if (DBQueries.cartList.size() == 0) {
                        DBQueries.loadCartList(ProductDetailsActivity.this, false);
                    }
                    if (DBQueries.myRatedIds.contains(productID)) {
                        int index = DBQueries.myRatedIds.indexOf(productID);
                        initialRating = Integer.parseInt(String.valueOf(DBQueries.myRating.get(index))) - 1;
                        setRating(initialRating);
                    }

                    if (DBQueries.cartList.contains(productID)) {
                        ALREADY_ADDED_TO_CART = true;
                    } else {
                        ALREADY_ADDED_TO_CART = false;
                    }
                    if (DBQueries.wishList.contains(productID)) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    } else {
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running_wishlist_query) {
                    running_wishlist_query = true;
                    if (ALREADY_ADDED_TO_WISHLIST) {
                        int index = DBQueries.wishList.indexOf(productID);
                        DBQueries.removeFromWishList(index, ProductDetailsActivity.this);
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
                    } else {
                        addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put("product_ID_" + String.valueOf(DBQueries.wishList.size()), productID);
                        addProduct.put("list_size", (long) (DBQueries.wishList.size() + 1));
                        firebaseFirestore.collection("USERS").document(MainActivity.currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    if (DBQueries.wishListModelList.size() != 0) {
                                        DBQueries.wishListModelList.add(new WishListModel(productID, documentSnapshot.get("product_image_1").toString(),
                                                documentSnapshot.get("product_full_title").toString(),
                                                documentSnapshot.get("average_rating").toString(),
                                                (long) documentSnapshot.get("total_ratings"),
                                                documentSnapshot.get("product_price").toString(),
                                                documentSnapshot.get("cutted_price").toString()
                                        ));
                                    }

                                    ALREADY_ADDED_TO_WISHLIST = true;
                                    addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                    DBQueries.wishList.add(productID);
                                    Toast.makeText(ProductDetailsActivity.this, "Added to wishlist successfully", Toast.LENGTH_SHORT).show();


                                } else {
                                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));

                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                                running_wishlist_query = false;
                            }
                        });


                    }
                }
            }
        });

        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // Rating Layout logic Starts
        initialRating = 0;

        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (starPosition != initialRating) {
                        if (!running_rating_query) {
                            running_rating_query = true;
                            setRating(starPosition);
                            Map<String, Object> udpateRating = new HashMap<>();
                            if (DBQueries.myRatedIds.contains(productID)) {

                                TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);


                                udpateRating.put(initialRating + 1 + "_star", Long.parseLong(oldRating.getText().toString()) - 1);
                                udpateRating.put(starPosition + 1 + "_star", Long.parseLong(finalRating.getText().toString()) + 1);
                                udpateRating.put("average_rating", calculateAverageRating((long) starPosition - initialRating, true));

                            } else {
                                udpateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                udpateRating.put("average_rating", calculateAverageRating((long) starPosition + 1, false));
                                udpateRating.put("total_rating", (long) documentSnapshot.get("total_ratings") + 1);
                            }

                            firebaseFirestore.collection("PRODUCTS").document(productID)
                                    .update(udpateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Map<String, Object> myRating = new HashMap<>();
                                        if (DBQueries.myRatedIds.contains(productID)) {
                                            myRating.put("rating_" + DBQueries.myRatedIds.indexOf(productID), (long) starPosition + 1);
                                        } else {
                                            myRating.put("list_size", (long) DBQueries.myRatedIds.size() + 1);
                                            myRating.put("product_ID_" + DBQueries.myRatedIds.size(), productID);
                                            myRating.put("rating_" + DBQueries.myRatedIds.size(), (long) starPosition + 1);
                                        }


                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATINGS")
                                                .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    if (DBQueries.myRatedIds.contains(productID)) {
                                                        DBQueries.myRating.set(DBQueries.myRatedIds.indexOf(productID), (long) starPosition + 1);


                                                        TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                        TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                        oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                        finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));


                                                    } else {


                                                        DBQueries.myRatedIds.add(productID);
                                                        DBQueries.myRating.add((long) starPosition + 1);

                                                        TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                        rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                        totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ") ratings");
                                                        totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " ratings");
                                                        totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));
                                                        Toast.makeText(ProductDetailsActivity.this, "Your rating is added to the product", Toast.LENGTH_SHORT).show();
                                                    }
                                                    for (int x = 0; x < 5; x++) {

                                                        TextView ratingFigures = (TextView) ratingsNoContainer.getChildAt(x);

                                                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                        int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                        progressBar.setMax(maxProgress);
                                                        progressBar.setProgress(Integer.parseInt(ratingFigures.getText().toString()));
                                                    }
                                                    initialRating = starPosition;
                                                    averageRating.setText(calculateAverageRating(0, true));
                                                    averageRatingMiniView.setText(calculateAverageRating(0, true));

                                                    if (DBQueries.wishList.contains(productID) && DBQueries.wishListModelList.size() != 0) {
                                                        int index = DBQueries.wishList.indexOf(productID);
                                                        DBQueries.wishListModelList.get(index).setRating(averageRating.getText().toString());
                                                        DBQueries.wishListModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));

                                                    }

                                                } else {
                                                    setRating(initialRating);
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                                running_rating_query = false;
                                            }
                                        });
                                    } else {
                                        running_rating_query = false;
                                        setRating(initialRating);
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }

                }
            });
        }
        // Rating Layout logic Ends

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDetailsActivity = ProductDetailsActivity.this;
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, AddAddressActivity.class);
                startActivity(deliveryIntent);
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running_cart_query) {
                    running_cart_query = true;
                    if (ALREADY_ADDED_TO_CART) {
                        running_cart_query = false;
                        Toast.makeText(ProductDetailsActivity.this, "Already added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put("product_ID_" + String.valueOf(DBQueries.cartList.size()), productID);
                        addProduct.put("list_size", (long) (DBQueries.cartList.size() + 1));
                        firebaseFirestore.collection("USERS").document(MainActivity.currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    if (DBQueries.cartItemModelList.size() != 0) {
                                        DBQueries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString(),
                                                documentSnapshot.get("product_title").toString(),
                                                documentSnapshot.get("product_price").toString(),
                                                documentSnapshot.get("cutted_price").toString(),
                                                (long) 1,
                                                (long) 0
                                        ));
                                    }
                                    ALREADY_ADDED_TO_CART = true;
                                    DBQueries.cartList.add(productID);
                                    Toast.makeText(ProductDetailsActivity.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                                    running_cart_query = false;
                                } else {
                                    running_cart_query = false;
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }
            }
        });


    }

    @Override
    protected void onStart() {

        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (DBQueries.wishList.size() == 0) {
            DBQueries.loadWishList(ProductDetailsActivity.this, false);
        }

        if (DBQueries.myRating.size() == 0) {
            DBQueries.loadRatingList(ProductDetailsActivity.this);
        }

        if (DBQueries.cartList.size() == 0) {
            DBQueries.loadCartList(ProductDetailsActivity.this, false);
        }

        if (DBQueries.myRatedIds.contains(productID)) {
            int index = DBQueries.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBQueries.myRating.get(index))) - 1;
            setRating(initialRating);
        }

        if (DBQueries.cartList.contains(productID)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }

        if (DBQueries.wishList.contains(productID)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
            ALREADY_ADDED_TO_WISHLIST = false;
        }

    }

    // Reflect the rating set by the user in terms of stars
    public static void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }

    }
    // Calculate based on the rating given by the user on the fly
    private String calculateAverageRating(long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * x);
        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf((totalStars / Long.parseLong(totalRatingsFigure.getText().toString()))).substring(0, 3);
        } else {
            return String.valueOf((totalStars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1))).substring(0, 3);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            productDetailsActivity = null;
            finish();
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        productDetailsActivity = null;
    }
}
