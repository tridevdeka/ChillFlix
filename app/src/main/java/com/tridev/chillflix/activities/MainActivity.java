package com.tridev.chillflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;

import com.tridev.chillflix.R;
import com.tridev.chillflix.databinding.ActivityMainBinding;
import com.tridev.chillflix.utilities.AppUtils;
import com.tridev.chillflix.utilities.NetworkChangeListener;


import dagger.hilt.android.AndroidEntryPoint;
import me.ibrahimsn.lib.OnItemSelectedListener;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavController navController;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
    }

    void doInitialization() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        setNavController();
    }

    void setNavController() {
        binding.smoothBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {

            switch (i) {
                case 0:
                    navController.navigate(R.id.home2);
                    return true;

                case 1:
                    navController.navigate(R.id.favorite);
                    return true;

                case 2:
                    navController.navigate(R.id.searchMovies);
                    return true;

            }
            return false;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        binding.smoothBottomBar.setupWithNavController(menu, navController);
        return true;
    }


    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
        AppUtils.deleteCache(getApplicationContext());
    }

}