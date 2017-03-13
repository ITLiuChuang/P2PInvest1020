package com.p2pinvest1020.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildCount() == 0) {
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
                    childView.layout(childView.getLeft(),
                            childView.getTop() + y / 2,
                            childView.getRight(),
                            childView.getBottom() + y / 2);
                }

                lasty = eventY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
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
