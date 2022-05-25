package com.example.pushkinapp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pushkinapp.R;
import com.example.pushkinapp.view.CatalogFragment;
import com.example.pushkinapp.view.MapFragment;
import com.example.pushkinapp.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_catalog);

    }

    /**
     * The First fragment.
     */
    final CatalogFragment firstFragment = new CatalogFragment();
    /**
     * The Second fragment.
     */
    final SearchFragment secondFragment = new SearchFragment();
    /**
     * The Third fragment.
     */
    final MapFragment thirdFragment = new MapFragment();
    /**
     * The Fm.
     */
    final FragmentManager fm = getSupportFragmentManager();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_catalog:
                fm.beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.nav_search:
                fm.beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.nav_map:
                fm.beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        fm.popBackStack();
    }
}