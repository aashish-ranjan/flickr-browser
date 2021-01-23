package com.aashishranjan.flickrbrowser;

class Photo {
    String mTitle;
    String mLink;
    String mImageUrl;
    String mAuthor;
    String mAuthorId;
    String mTags;

    String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    String getLink() {
        return mLink;
    }

    void setLink(String link) {
        mLink = link;
    }

    String getImageUrl() {
        return mImageUrl;
    }

    void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    String getAuthor() {
        return mAuthor;
    }

    void setAuthor(String author) {
        mAuthor = author;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    void setAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    String getTags() {
        return mTags;
    }

    void setTags(String tags) {
        mTags = tags;
    }

    @Override
    public String toString() {
        return "Photo{" +
            "mTitle='" + mTitle + '\'' +
            ", mLink='" + mLink + '\'' +
            ", mImageUrl='" + mImageUrl + '\'' +
            ", mAuthor='" + mAuthor + '\'' +
            ", mAuthorId='" + mAuthorId + '\'' +
            ", mTags='" + mTags + '\'' +
            '}';
    }
}
