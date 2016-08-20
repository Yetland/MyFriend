package com.yetland.myfriend.model;

import java.io.Serializable;

/**
 * Created by yeliang on 2016/4/20.
 */
public class ImageItem implements Serializable{
    private String imageId;
    private String contentMessage;
    private String url;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "imageId='" + imageId + '\'' +
                ", contentMessage='" + contentMessage + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
