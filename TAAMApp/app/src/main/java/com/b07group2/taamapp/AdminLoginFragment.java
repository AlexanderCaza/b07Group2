package com.b07group2.taamapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

public class AdminLoginFragment extends Fragment {

    private final String username = "admin";
    private final String password = "pass";

    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_fragment, container, false);

        Button loginButton = view.findViewById(R.id.loginButton);
        usernameInput = view.findViewById(R.id.usernameInput);
        passwordInput = view.findViewById(R.id.passwordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verifyUsernameAndPassword()) {
                    loadFragment(new HomeAdminFragment());
                }
            }
        });
        return view;
    }
    protected void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean verifyUsernameAndPassword() {
        if(usernameInput.getEditText().getText().toString().equals(username) &&
                passwordInput.getEditText().getText().toString().equals(password)) {
            usernameInput.setError(null);
            passwordInput.setError(null);
            return true;
        }
        usernameInput.setError(" ");
        passwordInput.setError("Username or Password is Incorrect*");
        return false;
    }
}
