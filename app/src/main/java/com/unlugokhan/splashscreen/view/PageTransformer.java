package com.unlugokhan.splashscreen.view;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class PageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        if (position < -1) {
            view.setAlpha(0);

        } else if (position <= 0) {
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) {
            view.setTranslationX(-position * view.getWidth());
            view.setAlpha(1 - Math.abs(position));
            view.setScaleX(1 - Math.abs(position));
            view.setScaleY(1 - Math.abs(position));
        } else {
            view.setAlpha(0);
        }
    }
}