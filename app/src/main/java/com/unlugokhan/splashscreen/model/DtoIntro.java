package com.unlugokhan.splashscreen.model;

import android.graphics.drawable.Drawable;

public class DtoIntro {
    public int imageUrl;

    public String name;
    public String brief;

    public DtoIntro(int imageUrl, String name, String brief) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.brief = brief;
    }

    public DtoIntro() {
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
