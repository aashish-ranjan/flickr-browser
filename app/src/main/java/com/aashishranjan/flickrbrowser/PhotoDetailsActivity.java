package com.aashishranjan.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        activateToolbar(true);

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null) {
            TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
            photoAuthor.setText("Author: " + photo.getAuthor());

            TextView photoTitle = (TextView) findViewById(R.id.photo_title);
            photoTitle.setText("Title: " + photo.getTitle());

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            photoTags.setText("Tags: " + photo.getTags());

            ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
            Picasso.get()
                .load(photo.getLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
        }
    }
}