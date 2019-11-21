package com.example.dontpadapplication;

import android.widget.ImageView;

public class TagContent {

    private String text;
    private String tag;
    private ImageView image;

    public TagContent(){
    }
    public TagContent(String text, String tag, ImageView image) {
        this.text = text;
        this.tag = tag;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
