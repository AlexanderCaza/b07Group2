package com.b07group2.taamapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(new HomeUserFragment(), R.id.fragment_home_container);
        }
    }

    private void loadFragment(Fragment fragment, int containerId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_home_container);

        if (currentFragment instanceof HomeFragment) {
            HomeFragment homeFragment = (HomeFragment) currentFragment;
            if (homeFragment.isVisible() && homeFragment.canGoBack()) {
                homeFragment.goToPreviousPage();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}