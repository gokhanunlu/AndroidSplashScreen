package com.unlugokhan.splashscreen.view;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.unlugokhan.splashscreen.R;
import com.unlugokhan.splashscreen.model.DtoIntro;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private List<DtoIntro> items;
    private Context context;

    public ViewPagerAdapter(Context context, List<DtoIntro> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_intro, container, false);
        ImageView viewById = view.findViewById(R.id.image);
        ((TextView) view.findViewById(R.id.title)).setText(items.get(position).name);
        ((TextView) view.findViewById(R.id.description)).setText(items.get(position).brief);
        Glide.with(context)
                .load(items.get(position).imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(viewById);
        container.addView(view);

        return view;
    }
}
