package com.appb.app.appb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by qati on 06.05.15.
 */
public class ViewUtils {

    public static final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static void makeVisible(View v) {
        if (v != null && v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
    }

    public static void makeVisible(List<View> v) {
        if (v != null) ButterKnife.apply(v, SHOW);
    }


    public static void makeInvisible(View v) {
        if (v != null && v.getVisibility() != View.INVISIBLE) v.setVisibility(View.INVISIBLE);
    }

    public static void makeGone(View v) {
        if (v != null && v.getVisibility() != View.GONE) v.setVisibility(View.GONE);
    }

    public static void makeGone(List<View> v) {
        if (v != null) ButterKnife.apply(v, GONE);
    }

    public static ViewGroup.LayoutParams menuItemParams(Context a) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dpToPx(a, 50));
    }

    public static LinearLayout.LayoutParams getMatchParentParams() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public static final ButterKnife.Action GONE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            if (view.getVisibility() != View.GONE) view.setVisibility(View.GONE);
        }
    };

    public static final ButterKnife.Action SHOW = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            if (view.getVisibility() != View.VISIBLE) view.setVisibility(View.VISIBLE);
        }
    };

    public static void setVisibleOrGone(View view, boolean isVisible) {
        if (isVisible) {
            makeVisible(view);
        } else {
            makeGone(view);
        }
    }

    public static void setVisible(View view, boolean isVisible) {
        if (isVisible) {
            makeVisible(view);
        } else {
            makeInvisible(view);
        }
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density / 2));
        a.setDuration(130);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static Bitmap getBitMapFromlayout(Context context, int layout) {
        View v = LayoutInflater.from(context).inflate(layout, null);

        if (v.getMeasuredHeight() <= 0) {
            v.measure(ViewUtils.dpToPx(context, 41), ViewUtils.dpToPx(context, 64));
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }

        Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

//    public static void expand(Context ctx, View v) {
//
//        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.expand);
//        if (a != null) {
//            a.reset();
//            if (v != null) {
//                v.clearAnimation();
//                v.startAnimation(a);
//            }
//        }
//    }
//
//    public static void collapse(Context ctx, View v) {
//        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.collapse);
//        if (a != null) {
//            a.reset();
//            if (v != null) {
//                v.clearAnimation();
//                v.startAnimation(a);
//            }
//        }
//    }
//
//    public static void move(Context ctx, View v) {
//
//        TranslateAnimation animation = new TranslateAnimation(v.getX(), v.getX(), v.getY(), v.getY() - 200);
//        animation.setDuration(1000);
//        animation.setFillAfter(false);
////        animation.setAnimationListener(new MyAnimationListener());
//        v.startAnimation(animation);
//    }


}
