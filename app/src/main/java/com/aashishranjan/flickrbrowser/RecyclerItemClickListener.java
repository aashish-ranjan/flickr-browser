package com.aashishranjan.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    interface RecyclerTouchListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private RecyclerTouchListener mRecyclerTouchListener;
    private GestureDetectorCompat mGestureDetector;

    RecyclerItemClickListener(Context context, final RecyclerView recyclerView, RecyclerTouchListener recyclerTouchListener) {
        mRecyclerTouchListener = recyclerTouchListener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "onSingleTapConfirmed: called");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mRecyclerTouchListener != null) {
                    mRecyclerTouchListener.onItemClick(childView, recyclerView.getChildLayoutPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: called");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mRecyclerTouchListener != null) {
                    mRecyclerTouchListener.onItemLongClick(childView, recyclerView.getChildLayoutPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: called");
        if (mGestureDetector != null) {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent() called with result " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent() called with reesult " + false);
            return false;
        }
    }
}
