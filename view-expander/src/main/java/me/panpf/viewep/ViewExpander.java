/*
 * Copyright (C) 2015 Peng fei Pan <sky@panpf.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.panpf.viewep;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;

/**
 * 用于快捷实现 View 展开关闭效果
 * <br>
 * <br>首先 new {@link ViewExpander}，注意 {@link ViewExpander} 有一个抽象方法 {@link #setViewStatus(boolean)} 需要你重写，在这个方法里根据不同的状态设置 View
 * <br>然后调用 {@link #measureViewHeightAndInit(boolean)} 方法重置相关属性并初始化 View 状态
 * <br>最后调用 {@link #expand(boolean)} 方法展开或关闭View
 * <br>
 * <br>{@link ViewExpander} 可用于任何地方，特别是 {@link android.widget.ListView} 中
 */
public abstract class ViewExpander {
    private View view;
    private int fillParent;
    private int minHeight;
    private int maxHeight;
    private MyAnimationListener animationListener;

    @SuppressWarnings("WeakerAccess")
    public ViewExpander(View view) {
        this.view = view;
        this.fillParent = view.getResources().getDisplayMetrics().widthPixels;
        this.animationListener = new MyAnimationListener();
    }

    @SuppressWarnings("unused")
    public View getOpeningView() {
        return view;
    }

    /**
     * 设置 View 的状态
     *
     * @param expanded true：展开；false：折叠
     */
    public abstract void setViewStatus(boolean expanded);

    /**
     * 测量 View 在不同状态下的高度，并初始化 View 的状态
     *
     * @param expanded 当前状态
     * @return false：展开和折叠时高度是一样的，不需要使用 {@link ViewExpander}
     */
    public boolean measureViewHeightAndInit(boolean expanded) {
        // 要先将高度设置为 wrap_content，要不然测量高度就不准确了
        LayoutParams layoutParams = view.getLayoutParams();
        applyValue(layoutParams, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        // 切换到反状态测量
        boolean currentStatus = !expanded;
        setViewStatus(currentStatus);
        if (view.getVisibility() != View.GONE) {
            view.measure(
                    MeasureSpec.makeMeasureSpec(fillParent, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            );
            if (currentStatus) {
                maxHeight = view.getMeasuredHeight();
            } else {
                minHeight = view.getMeasuredHeight();
            }
        } else {
            if (currentStatus) {
                maxHeight = 0;
            } else {
                minHeight = 0;
            }
        }

        // 切换初始状态测量
        currentStatus = expanded;
        setViewStatus(currentStatus);
        if (view.getVisibility() != View.GONE) {
            view.measure(
                    MeasureSpec.makeMeasureSpec(fillParent, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            );
            if (currentStatus) {
                maxHeight = view.getMeasuredHeight();
            } else {
                minHeight = view.getMeasuredHeight();
            }
        } else {
            if (currentStatus) {
                maxHeight = 0;
            } else {
                minHeight = 0;
            }
        }

        // 初始化状态以及高度
        applyValue(layoutParams, expanded ? maxHeight : minHeight);
        view.setLayoutParams(layoutParams);

        return minHeight != maxHeight;
    }

    /**
     * 初始化 View 的状态
     *
     * @param expanded 当前状态
     */
    @SuppressWarnings("unused")
    public void setViewHeightAndInit(boolean expanded, int maxHeight, int minHeight) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;

        // 初始化状态以及高度
        setViewStatus(expanded);
        LayoutParams layoutParams = view.getLayoutParams();
        applyValue(layoutParams, expanded ? maxHeight : minHeight);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 展开或折叠 View
     *
     * @param expanded true：展开 View；false：折叠 View
     */
    public void expand(boolean expanded) {
        MyAnimation animation;
        int fromValue;
        if (view.getVisibility() != View.GONE) {
            fromValue = getCurrentValueFromView(view);
        } else {
            fromValue = 0;
        }
        if (expanded) {
            animation = new MyAnimation(view, fromValue, maxHeight);
            setViewStatus(true);
        } else {
            animation = new MyAnimation(view, fromValue, minHeight);
            animation.setAnimationListener(animationListener);
        }
        animation.setDuration(200L);
        view.startAnimation(animation);
    }

    @SuppressWarnings("WeakerAccess")
    protected int getCurrentValueFromView(View view) {
        return view.getHeight();
    }

    @SuppressWarnings("WeakerAccess")
    protected void applyValue(LayoutParams layoutParams, int value) {
        layoutParams.height = value;
    }

    private class MyAnimation extends Animation {
        private int number;
        private int from;
        private View view;

        MyAnimation(View view, int from, int to) {
            this.view = view;
            this.from = from;
            this.number = to - from;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            LayoutParams param = view.getLayoutParams();
            applyValue(param, from + (int) (number * interpolatedTime));
            view.setLayoutParams(param);
        }
    }

    private class MyAnimationListener implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            setViewStatus(false);
        }
    }
}
