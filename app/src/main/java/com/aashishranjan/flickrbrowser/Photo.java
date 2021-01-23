package com.aashishranjan.flickrbrowser;

class Photo {
    private String mTitle;
    private String mLink;
    private String mImageUrl;
    private String mAuthor;
    private String mAuthorId;
    private String mTags;

    Photo(String title, String link, String imageUrl, String author, String authorId, String tags) {
        mTitle = title;
        mLink = link;
        mImageUrl = imageUrl;
        mAuthor = author;
        mAuthorId = authorId;
        mTags = tags;
    }

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
