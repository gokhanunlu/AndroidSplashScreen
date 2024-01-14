package com.unlugokhan.splashscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.unlugokhan.splashscreen.model.DtoIntro;
import com.unlugokhan.splashscreen.util.FixedSpeedScroller;
import com.unlugokhan.splashscreen.util.Tools;
import com.unlugokhan.splashscreen.view.PageTransformer;
import com.unlugokhan.splashscreen.view.ViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private int max_step = 0;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Button btnNext;
    private List<DtoIntro> items = new ArrayList<>();
    private ImageView path4, path7, path9, path10;

    private int[] translationXValues = {-30, 40, 100, 160, 220};
    private int[] translationYValues = {-30, 40, 100, 160, 220};
    private int[] rotationValues = {245, 250, 255, 260, 265};
    private int currentColor; // initial color is not set here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        currentColor = getResources().getColor(R.color.p1);
        setupSystemBar();
        generateCustomList();
        max_step = items.size();
        initComponent();
    }

    private void setupSystemBar() {
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }


    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[max_step];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int height = 8;
            int width = i == current_index ? (height * 8) : (height * 4);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width, height));
            params.setMargins(2, 10, 2, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_rectangle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        dots[current_index].setImageResource(R.drawable.shape_rectangle);
        dots[current_index].setColorFilter(getResources().getColor(R.color.orange_700), PorterDuff.Mode.SRC_IN);
    }

    private void changeViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), new LinearInterpolator());
            scrollerField.set(viewPager, scroller);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
            animateImageViews(position);
            if (position == max_step - 1) {
                btnNext.setText("BAŞLAYALIM !");

            } else {
                btnNext.setText(getString(R.string.NEXT));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private int[] colorArray = new int[]{
            R.color.p1,
            R.color.p2,
            R.color.p3,
            R.color.p4,
            R.color.p5
    };

    private void animateColorChange(ImageView imageView, int colorTo) {
        int colorFrom = currentColor;
        ValueAnimator colorAnim = ValueAnimator.ofArgb(colorFrom, colorTo);
        colorAnim.setDuration(250);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        });
        colorAnim.start();
        currentColor = colorTo;
    }

    private void animateImageViews(int position) {
        path4.animate().translationX(translationXValues[position]).setDuration(250).start();
        path7.animate().translationY(translationYValues[position]).setDuration(250).start();
        path9.animate().translationY(translationYValues[position]).setDuration(250).start();
        path10.animate().translationY(-translationYValues[position]).rotation(rotationValues[position]).setDuration(250).start();

        int colorTo = getResources().getColor(colorArray[position]);
        animateColorChange(path4, colorTo);
        animateColorChange(path7, colorTo);
        animateColorChange(path9, colorTo);
        animateColorChange(path10, colorTo);
    }

    private void initComponent() {
        path4 = findViewById(R.id.path4);
        path7 = findViewById(R.id.path7);
        path9 = findViewById(R.id.path9);
        path10 = findViewById(R.id.path10);
        viewPager = findViewById(R.id.view_pager);
        btnNext = findViewById(R.id.btn_next);
        changeViewPagerScroller();
        bottomProgressDots(0);
        viewPagerAdapter = new ViewPagerAdapter(this, items);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setPageTransformer(false, new PageTransformer());
        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem() + 1;
            if (current < max_step) {
                viewPager.setCurrentItem(current);
            } else {
                finish();
            }
        });

        ((ImageButton) findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void generateCustomList() {
        items.add(new DtoIntro(R.drawable.p_1,
                "Lorem ipsum dolor sit amet",
                "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));

        items.add(new DtoIntro(R.drawable.p_2, "Ut enim ad minim veniam", "Quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));
        items.add(new DtoIntro(R.drawable.p_3, "Duis aute irure dolor", "In reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."));
        items.add(new DtoIntro(R.drawable.illustration_orange_04, "Excepteur sint occaecat", "Cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        items.add(new DtoIntro(R.drawable.illustration_orange_04, "Sed ut perspiciatis", "Unde omnis iste natus error sit voluptatem accusantium doloremque laudantium."));
    }


}