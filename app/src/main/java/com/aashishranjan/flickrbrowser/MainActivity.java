package com.aashishranjan.flickrbrowser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonDataProcessor.JsonDataProvider, RecyclerItemClickListener.RecyclerTouchListener {
    private static final String TAG = "MainActivity";
    private static final String baseUrl = "https://www.flickr.com/services/feeds/photos_public.gne";

    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView flickrRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);
        flickrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
        flickrRecyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        flickrRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, flickrRecyclerView, this));

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        JsonDataProcessor dataProcessor = new JsonDataProcessor(this, baseUrl, "en-us", false);
//        dataProcessor.executeOnSameThread("aashish,ranjan");
        dataProcessor.execute("aashish,ranjan");
        super.onResume();
        Log.d(TAG, "onResume ends");
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
            mFlickrRecyclerViewAdapter.loadNewData(data);
        } else {
            //download or processing failed
            Log.e(TAG, "onDownloadComplete: Failed with status " + downloadStatus);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: called");
        Toast.makeText(this, "item clicked at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: called");
        Toast.makeText(this, "item long clicked at position " + position, Toast.LENGTH_SHORT).show();
    }

}
