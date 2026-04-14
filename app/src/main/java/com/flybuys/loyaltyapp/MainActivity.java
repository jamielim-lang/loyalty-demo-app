package com.flybuys.loyaltyapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.User;
import com.flybuys.loyaltyapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private String currentScreen = AnalyticsEvent.SCREEN_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        }

        // Track navigation tab changes
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            String tabName = getTabName(item.getItemId());
            AnalyticsManager.getInstance().trackNavigation(tabName, currentScreen);
            currentScreen = tabName;
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        // Initialize user analytics properties
        User mockUser = User.getMockUser();
        AnalyticsManager.getInstance().setUserProperties(mockUser);

        // Track app open
        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_HOME);
    }

    private String getTabName(int itemId) {
        if (itemId == R.id.navigation_home) return AnalyticsEvent.SCREEN_HOME;
        if (itemId == R.id.navigation_collect) return AnalyticsEvent.SCREEN_COLLECT;
        if (itemId == R.id.navigation_wallet) return AnalyticsEvent.SCREEN_WALLET;
        if (itemId == R.id.navigation_redeem) return AnalyticsEvent.SCREEN_REDEEM;
        if (itemId == R.id.navigation_activity) return AnalyticsEvent.SCREEN_ACTIVITY;
        return "Unknown";
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsManager.getInstance().trackScreenView(currentScreen);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsManager.getInstance().flush();
    }

    public BottomNavigationView getBottomNavigation() {
        return binding.bottomNavigation;
    }
}
