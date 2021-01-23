package com.aashishranjan.flickrbrowser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements RawDataDownloader.DownloadCallback {
    private static final String TAG = "MainActivity";
    private static final String url = "https://www.flickr.com/services/feeds/photos_public.gne?tags=aashish&format=json&nojsoncallback=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataDownloader dataDownloader = new DataDownloader(this);
        dataDownloader.execute(url);
        Log.d(TAG, "onCreate: ends");
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
    public void onDownloadComplete(String data, DownloadStatus downloadStatus) {
        if (downloadStatus == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete: Downloaded data is: " + data);
        } else {
            //download or processing failed
            Log.e(TAG, "onDownloadComplete: Failed with status " + downloadStatus);
        }
    }

}
