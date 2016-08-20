package com.yetland.myfriend.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yetland.myfriend.R;

import java.util.List;

/**
 * Created by 亮 on 2016/3/4.
 */
public class MainActivityPagerAdapter extends PagerAdapter {

    private static final String TAG = "MainActivityPagerAdapter";
    private Context context;
    private List<String> imageId;

    public MainActivityPagerAdapter(Context context, List<String> imageId) {
        this.context = context;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {
        return imageId.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);

//        Drawable bitmapDrawable = container.getResources().getDrawable(imageId.get(position));
//        iv.setImageDrawable(bitmapDrawable);

        String url = imageId.get(position);
        Picasso.with(context)
                .load(url)
                .resize(640, 360)
                .centerCrop()
                .placeholder(R.mipmap.img_custom)
                .into(iv);

        container.addView(iv, 0);
        iv.setOnClickListener(v -> Log.e(TAG, "onClick:" + position));
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
