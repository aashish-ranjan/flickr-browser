package com.aashishranjan.flickrbrowser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonDataProcessor.JsonDataProvider {
    private static final String TAG = "MainActivity";
    private static final String baseUrl = "https://www.flickr.com/services/feeds/photos_public.gne";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        JsonDataProcessor dataProcessor = new JsonDataProcessor(this);
        dataProcessor.executeOnSameThread(baseUrl, "en-us", false, "aashish,ranjan");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu: returned " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        Log.d(TAG, "onOptionsItemSelected: returned");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus downloadStatus) {
        if (downloadStatus == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete: Downloaded data is: " + data);
        } else {
            //download or processing failed
            Log.e(TAG, "onDownloadComplete: Failed with status " + downloadStatus);
        }
    }

}
