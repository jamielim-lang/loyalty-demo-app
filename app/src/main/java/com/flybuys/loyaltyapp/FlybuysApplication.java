package com.flybuys.loyaltyapp;

import android.app.Application;

import com.flybuys.loyaltyapp.analytics.AnalyticsManager;

public class FlybuysApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsManager.getInstance().initialize(this);
    }
}
