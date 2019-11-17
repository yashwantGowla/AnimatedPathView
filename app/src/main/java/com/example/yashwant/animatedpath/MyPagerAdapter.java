package com.example.yashwant.animatedpath;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class MyPagerAdapter extends PagerAdapter {

    Context context;
    int[] listItems;
    int adapterType;

    public MyPagerAdapter(Context context, int[] listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cover, null);
        //try {

        RelativeLayout linMain = view.findViewById(R.id.linMain);
        ImageView imageCover = view.findViewById(R.id.imageCover);
        final AnimatedPathView animatedPathView = view.findViewById(R.id.animated_path);

        linMain.setTag(position);

        ViewTreeObserver observer = animatedPathView.getViewTreeObserver();

        if (observer != null) {

            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    animatedPathView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    float[][] points = new float[][]{
                            {0, 0},
                            {imageCover.getWidth(), 0},
                            {imageCover.getWidth(), imageCover.getHeight()},
                            {0, imageCover.getHeight()},
                            {0, 0}
                    };

                    animatedPathView.setPath(points);

                    Log.e("adapter", "instantiateItem: ");
                }
            });
        }

        animatedPathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(view, "percentage", 0.0f, 1.0f);
                anim.setDuration(6000);
                anim.setInterpolator(new LinearInterpolator());
                anim.start();

            }
        });

        animatedPathView.performClick();
//            linMain.setBackgroundResource(R.drawable.shadow);

        Glide.with(context)
                .load(listItems[position])
                .into(imageCover);

        container.addView(view);

        /*} catch (Exception e) {
            e.printStackTrace();
        }*/

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listItems.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}