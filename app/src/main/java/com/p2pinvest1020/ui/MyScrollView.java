package com.p2pinvest1020.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by 刘闯 on 2017/3/13.
 */

public class MyScrollView extends ScrollView {
    private int lasty;
    private View childView;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    private Rect rect = new Rect();
    private boolean isAnimtionEnd = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildCount() == 0 || !isAnimtionEnd) {
            return super.onTouchEvent(ev);
        }
        /**
         * getY(); 相对于父布局的Y值
         * getrawY(); 相对于屏幕的Y值
         * */
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //保存第一次触摸的位置
                lasty = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isNeedMove()) {
                    //移动的距离
                    int y = eventY - lasty;
                    //当我们没有记录的时候需要记录原来的位置
                    if (rect.isEmpty()) {
                        rect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                    }
                    //记录原来的位置
                    childView.layout(childView.getLeft(),
                            childView.getTop() + y / 2,
                            childView.getRight(),
                            childView.getBottom() + y / 2);
                }

                lasty = eventY;
                break;
            case MotionEvent.ACTION_UP:
                //当原来的位置有记录的时候并且动画是结束的时候再执行
                if (!rect.isEmpty() && isAnimtionEnd) {
                    //获取原来的位置和拉动距离的差
                    int transLateY = childView.getBottom() - rect.bottom;
                    //平移动画移动的距离
                    TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -transLateY);
                    animation.setDuration(200);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //当动画开始执行的时候设置成false
                            isAnimtionEnd = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //清除动画
                            childView.clearAnimation();
                            //回到原来的位置
                            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
                            //清除原来的位置
                            rect.setEmpty();
                            isAnimtionEnd = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    childView.startAnimation(animation);
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 事件拦截
     *
     * @param ev
     * @return
     */
    private int downy, lastx, downx;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isOnIntercept = false;
        int eventx = (int) ev.getX();
        int eventy = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lasty = downy = eventy;
                lastx = downx = eventx;

                break;
            case MotionEvent.ACTION_MOVE:
                int absx = Math.abs(eventx - downx);
                int absy = Math.abs(eventy - downy);
                if (absy > absx && absy >= 20) {
                    isOnIntercept = true;
                }
                lastx = eventx;
                lasty = eventy;

                break;

        }
        return isOnIntercept;

    }

    private boolean isNeedMove() {
        //得到scrollView的高度
        int measuredHeight = this.getMeasuredHeight();
        //得到子视图的高度
        int childHeight = childView.getMeasuredHeight();
        int y = childHeight - measuredHeight;
        //拿到滑动的距离  往下滑动是变小 往上滑动是变大
        if (getScrollY() <= 0 || getScrollY() <= y) {
            return true;
        }
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //这个方法是在布局加载完成后调用的
        //判断
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }
}
