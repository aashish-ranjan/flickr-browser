package com.aashishranjan.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, NOT_INITIALIZED, PROCESSING, EMPTY_OR_FAILED, OK}

class DataDownloader extends AsyncTask<String, Void, String> {
    private static final String TAG = "DataDownloader";

    private DownloadStatus downloadStatus;

    public DataDownloader() {
        this.downloadStatus = DownloadStatus.IDLE;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: The downloaded string is " + s);
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            this.downloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }
        try {
            this.downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Response code was " + response);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append('\n');
            }

            this.downloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO exception reading data: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security exception. Needs permission? " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
            }
        }

        this.downloadStatus = DownloadStatus.EMPTY_OR_FAILED;
        return null;
    }
}
