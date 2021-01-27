package com.aashishranjan.flickrbrowser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    void activateToolbar(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}
