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

    private JsonDataProvider mCallback;

    public JsonDataProcessor(JsonDataProvider dataProvider) {
        mCallback = dataProvider;
    }

    interface JsonDataProvider {
        void onDataAvailable(List<Photo> photoList, DownloadStatus status);
    }

    void executeOnSameThread(String baseUrl, String language, boolean matchAll, String tags) {
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUrl = constructUri(baseUrl, language, matchAll, tags);

        RawDataDownloader dataDownloader = new RawDataDownloader(this);
        dataDownloader.execute(destinationUrl);

        Log.d(TAG, "executeOnSameThread: ends");
    }

    private String constructUri(String baseUrl, String language, boolean matchAll, String tags) {
        Log.d(TAG, "constructUri: starts");

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
        List<Photo> photoList = new ArrayList<>();
        if (downloadStatus == DownloadStatus.OK) {

            try {
                JSONObject dataJsonObject = new JSONObject(data);
                JSONArray itemJsonArray = dataJsonObject.getJSONArray("items");

                for (int i = 0; i < itemJsonArray.length(); i++) {
                    JSONObject jsonPhoto = itemJsonArray.getJSONObject(i);

                    String title = jsonPhoto.getString("title");
                    String link = jsonPhoto.getString("link");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String imageUrl = jsonMedia.getString("m");
                    imageUrl = imageUrl.replaceFirst("_m", "_b");

                    Photo photo = new Photo(title, link, imageUrl, author, authorId, tags);
                    photoList.add(photo);
                }
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error in processing the json" + jsone.getMessage());
                downloadStatus = DownloadStatus.EMPTY_OR_FAILED;
            }
        }
        if (mCallback != null) {
            mCallback.onDataAvailable(photoList, downloadStatus);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }
}
