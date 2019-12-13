package com.example.shopquikr.View;


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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopquikr.Activity.MainActivity;
import com.example.shopquikr.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText password;
    private TextView forgotPassword;
    private ImageView closeButton;
    private Button signinButton;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailPatternRegex="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        dontHaveAnAccount = view.findViewById(R.id.dontHaveAnAccount);
        email=view.findViewById(R.id.signinEmailId);
        password=view.findViewById(R.id.signinPassword);
        closeButton=view.findViewById(R.id.signinCloseButton);
        signinButton=view.findViewById(R.id.signInButton);
        progressBar=view.findViewById(R.id.signinProgressBar);
        forgotPassword=view.findViewById(R.id.signinForgotPassword);
        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setFragment(new ResetPasswordFragment());
            }
        });
        //closeButton.setOnClickListener();
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
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signinButton.setEnabled(true);
                signinButton.setTextColor(Color.BLACK);
            }
            else{
                signinButton.setEnabled(false);
                signinButton.setTextColor(Color.LTGRAY);

            }
        }else{
            signinButton.setEnabled(false);
            signinButton.setTextColor(Color.LTGRAY);
        }
    }
    private void checkEmailAndPassword(){
        if(email.getText().toString().matches(emailPatternRegex)){
            if(password.length() >= 8 ){
                progressBar.setVisibility(View.VISIBLE);
                signinButton.setEnabled(false);
                signinButton.setTextColor(Color.LTGRAY);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent mainIntent=new Intent(getActivity(), MainActivity.class);
                            startActivity(mainIntent);
                            getActivity().finish();
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            signinButton.setEnabled(true);
                            signinButton.setTextColor(Color.BLACK);
                            String error=task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(getActivity(),"Incorrect email or password!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(),"Incorrect email or password!", Toast.LENGTH_SHORT).show();
        }


    }
}
