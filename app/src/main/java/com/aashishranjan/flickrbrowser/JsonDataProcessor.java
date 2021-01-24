package com.aashishranjan.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonDataProcessor implements RawDataDownloader.DownloadCallback {
    private static final String TAG = "JsonDataProcessor";

    private List<Photo> mPhotoList = null;

    private JsonDataProvider mCallback;
    private String mLanguage;
    private boolean mMatchAll;
    private String mTags;

    interface JsonDataProvider {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    JsonDataProcessor(JsonDataProvider callback, String language, boolean matchAll, String tags) {
        mCallback = callback;
        mLanguage = language;
        mMatchAll = matchAll;
        mTags = tags;
    }

    void executeOnSameThread(String baseUrl) {
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUrl = constructUri(baseUrl, mLanguage, mMatchAll, mTags);

        RawDataDownloader dataDownloader = new RawDataDownloader(this);
        dataDownloader.execute(destinationUrl);

        Log.d(TAG, "executeOnSameThread: ends");
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
        if (mCallback != null) {
            mCallback.onDataAvailable(photoList, downloadStatus);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }
}
