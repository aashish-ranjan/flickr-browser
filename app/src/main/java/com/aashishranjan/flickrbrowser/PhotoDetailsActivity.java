package com.aashishranjan.flickrbrowser;

import android.os.Bundle;

public class PhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        activateToolbar(true);
    }

}