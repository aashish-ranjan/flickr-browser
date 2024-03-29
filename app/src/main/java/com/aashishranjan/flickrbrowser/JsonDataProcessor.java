package com.aashishranjan.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonDataProcessor extends AsyncTask<String, Void, List<Photo>> implements RawDataDownloader.DownloadCallback {
    private static final String TAG = "JsonDataProcessor";

    private List<Photo> mPhotoList = null;

    private JsonDataProvider mCallback;
    private String mBaserUrl;
    private String mLanguage;
    private boolean mMatchAll;

    private boolean runningOnSameThread = false;
    private DownloadStatus mDownloadStatus = DownloadStatus.NOT_INITIALIZED;

    interface JsonDataProvider {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    JsonDataProcessor(JsonDataProvider callback, String baseUrl, String language, boolean matchAll) {
        mCallback = callback;
        mBaserUrl = baseUrl;
        mLanguage = language;
        mMatchAll = matchAll;
    }

    void executeOnSameThread(String searchTag) {
        Log.d(TAG, "executeOnSameThread: starts");
        runningOnSameThread = true;
        String destinationUrl = constructUri(mBaserUrl, mLanguage, mMatchAll, searchTag);

        RawDataDownloader dataDownloader = new RawDataDownloader(this);
        dataDownloader.execute(destinationUrl);

        Log.d(TAG, "executeOnSameThread: ends");
    }

    @Override
    protected void onPostExecute(List<Photo> photoList) {
        if (mCallback != null) {
            mCallback.onDataAvailable(mPhotoList, mDownloadStatus);
        }
        super.onPostExecute(photoList);
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");

        String destinationUrl = constructUri(mBaserUrl, mLanguage, mMatchAll, params[0]);

        RawDataDownloader dataDownloader = new RawDataDownloader(this);
        dataDownloader.runOnSameThread(destinationUrl);
        Log.d(TAG, "doInBackground ends");

        return mPhotoList;

    }

    private String constructUri(String baseUrl, String language, boolean matchAll, String tags) {
        Log.d(TAG, "constructUri: called");

        Uri uri = Uri.parse(baseUrl);
        Uri.Builder builder = uri.buildUpon();
        builder = builder.appendQueryParameter("tags", tags)
            .appendQueryParameter("tagmode", matchAll ? "all" : "any")
            .appendQueryParameter("format", "json")
            .appendQueryParameter("lang", language)
            .appendQueryParameter("nojsoncallback", "1");

        builder.build();
        return builder.toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus downloadStatus) {
        Log.d(TAG, "onDownloadComplete: starts");
        mPhotoList = new ArrayList<>();
        if (downloadStatus == DownloadStatus.OK) {

            try {
                JSONObject dataJsonObject = new JSONObject(data);
                JSONArray itemJsonArray = dataJsonObject.getJSONArray("items");

                for (int i = 0; i < itemJsonArray.length(); i++) {
                    JSONObject jsonPhoto = itemJsonArray.getJSONObject(i);

                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String imageUrl = jsonMedia.getString("m");

                    String link = imageUrl.replaceFirst("_m", "_b");

                    Photo photo = new Photo(title, link, imageUrl, author, authorId, tags);
                    mPhotoList.add(photo);
                }
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing the json" + jsone.getMessage());
                downloadStatus = DownloadStatus.EMPTY_OR_FAILED;
            }
        }
        if (runningOnSameThread && mCallback != null) {
            mCallback.onDataAvailable(mPhotoList, downloadStatus);
        }
        mDownloadStatus = downloadStatus;
        Log.d(TAG, "onDownloadComplete: ends");
    }
}
