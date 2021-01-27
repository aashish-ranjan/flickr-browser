package com.aashishranjan.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";

    private List<Photo> mPhotoList;
    private Context mContext;

    FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        mContext = context;
        mPhotoList = photoList;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        //created by the layout manager when the recycler view needs a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        Photo photo = mPhotoList.get(position);
        Picasso.get().load(photo.getImageUrl())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.thumbnail);

        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return (mPhotoList != null && mPhotoList.size() != 0) ? mPhotoList.size() : 0;
    }

    Photo getPhoto(int position) {
        return (mPhotoList != null && mPhotoList.size() != 0) ? mPhotoList.get(position) : null;
    }

    void loadNewData(List<Photo> photoList) {
        mPhotoList = photoList;
        notifyDataSetChanged();
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        FlickrImageViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
