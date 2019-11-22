package com.example.shopquikr;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;
    private ImageButton closeButton;
    private Button signupButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFireStore;
    private String emailPatternRegex="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        parentFrameLayout=getActivity().findViewById(R.id.registerFrameLayout);
        alreadyHaveAnAccount=view.findViewById(R.id.alreadyHaveAnAccount);
        email=view.findViewById(R.id.signupEmailId);
        fullName=view.findViewById(R.id.signupFullName);
        password=view.findViewById(R.id.signupPassword);
        confirmPassword=view.findViewById(R.id.signupConfirmPassword);
        closeButton=view.findViewById(R.id.signupCloseButton);
        signupButton=view.findViewById(R.id.signupButton);
        progressBar=view.findViewById(R.id.signupProgressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFireStore=FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SigninFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs(){
        if (!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fullName.getText())){
                if(!TextUtils.isEmpty(password.getText()) && password.getText().toString().length()>=8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())){
                        signupButton.setEnabled(true);
                        //signupButton.setTextColor(Color.argb(100,0,0,0));
                        signupButton.setTextColor(Color.BLACK);
                    }
                    else{
                        signupButton.setEnabled(false);
                        //signupButton.setTextColor(Color.argb(50,0, 0, 0));
                        signupButton.setTextColor(Color.LTGRAY);
                    }
                }
                else{
                    signupButton.setEnabled(false);
                    //signupButton.setTextColor(Color.argb(50,0, 0, 0));
                    signupButton.setTextColor(Color.LTGRAY);
                }
            }
            else{
                signupButton.setEnabled(false);
                //signupButton.setTextColor(Color.argb(50,0, 0, 0));
                signupButton.setTextColor(Color.LTGRAY);
            }
        } else{
            signupButton.setEnabled(false);
            //signupButton.setTextColor(Color.argb(50,0, 0, 0));
            signupButton.setTextColor(Color.LTGRAY);
        }
    }
    private void checkEmailAndPassword(){
        if (email.getText().toString().matches(emailPatternRegex)){
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                progressBar.setVisibility(View.VISIBLE);
                signupButton.setEnabled(false);
                //signupButton.setTextColor(Color.argb(50,0, 0, 0));
                signupButton.setTextColor(Color.LTGRAY);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<Object, String> customerData = new HashMap<>();
                            customerData.put("fullName", fullName.getText().toString());
                            firebaseFireStore.collection("CUSTOMERS").add(customerData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(mainIntent);
                                        getActivity().finish();
                                    }else{
                                        signupButton.setEnabled(true);
                                        //signupButton.setTextColor(Color.argb(100,0,0,0));
                                        signupButton.setTextColor(Color.BLACK);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            signupButton.setEnabled(true);
                            //signupButton.setTextColor(Color.argb(100,0,0,0));
                            signupButton.setTextColor(Color.BLACK);
                            progressBar.setVisibility(View.INVISIBLE);
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                confirmPassword.setError("Password doesn't match");
            }
        }else{
            email.setError("Email ID not valid");
        }
    }
}
