package com.arek314.neurodraw;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NeuroDrawApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setPreferences();
    }

    private void setPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }
}
