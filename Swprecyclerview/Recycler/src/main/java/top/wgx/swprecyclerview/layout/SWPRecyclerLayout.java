package top.wgx.swprecyclerview.layout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import top.wgx.swprecyclerview.interfaces.OnTouchUpListener;

/**
 * Created by wgx on 2016/7/27.
 */
public class SWPRecyclerLayout extends LinearLayout implements NestedScrollingParent {

    private Context context = null;
    private NestedScrollingParentHelper helper = null;
    private boolean IsRefresh = true;
    private boolean IsLoad = true;
    //move total
    private int totalY = 0;
    private LinearLayout headerLayout = null;
    private MyRecyclerView myRecyclerView = null;
    private LinearLayout footerLayout = null;
    private OnTouchUpListener onTouchUpListener = null;
    private boolean isfling = false;
    private int headerHeight = 0;
    private int footerHeight = 0;

    public SWPRecyclerLayout(Context context) {
        super(context);
        this.context = context;
        inital();
    }

    public SWPRecyclerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inital();
    }

    private void inital() {
        helper = new NestedScrollingParentHelper(this);
        headerLayout = new LinearLayout(context);
        myRecyclerView = new MyRecyclerView(context);
        footerLayout = new LinearLayout(context);
        setOrientation(VERTICAL);
        headerLayout.setOrientation(VERTICAL);
        footerLayout.setOrientation(VERTICAL);
        addView(this.headerLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(this.myRecyclerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(this.footerLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setMyRecyclerView(RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        myRecyclerView.setMyLayoutManager(layoutManager);
        myRecyclerView.setAdapter(adapter);
    }

    public void setMyRecyclerView(RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter, boolean fixed) {
        myRecyclerView.setMyLayoutManager(layoutManager);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setHasFixedSize(fixed);
    }

    /**
     * add headerview
     *
     * @param headerView
     * @param headerHeight
     */
    public void addHeaderView(View headerView, int headerHeight) {
        this.headerHeight = headerHeight;
        this.headerLayout.removeAllViews();
        this.headerLayout.addView(headerView);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, headerHeight);
        layoutParams.topMargin = -headerHeight;
        this.headerLayout.setLayoutParams(layoutParams);
    }

    /**
     * add footerview
     *
     * @param footerView
     * @param footerHeight
     */
    public void addFooterView(View footerView, int footerHeight) {
        this.footerHeight = footerHeight;
        this.footerLayout.removeAllViews();
        this.footerLayout.addView(footerView);
        this.footerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, footerHeight));
    }

    public void setScrollTo(int fromY, int toY) {
        smoothScrollTo((float) fromY, (float) toY);
    }

    public void setItemDivider(RecyclerView.ItemDecoration itemDecoration) {
        myRecyclerView.addItemDecoration(itemDecoration);
    }

    public int getTotal() {
        return -totalY / 2;
    }


    public void setIsScrollLoad(boolean isScrollLoad) {
        myRecyclerView.isScrollLoad = isScrollLoad;
    }

    public boolean isScrollLoad() {
        return myRecyclerView.isScrollLoad;
    }

    public void setIsScrollRefresh(boolean isScrollRefresh) {
        myRecyclerView.isScrollRefresh = isScrollRefresh;
    }

    public boolean isScrollRefresh() {
        return myRecyclerView.isScrollRefresh;
    }

    public void setRecyclerViewScrollToPosition(int position) {
        myRecyclerView.scrollToPosition(position);
    }

    public void addOnTouchUpListener(OnTouchUpListener onTouchUpListener) {
        this.onTouchUpListener = onTouchUpListener;
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        helper.onNestedScrollAccepted(child, target, axes);
    }

    /**
     * parent view intercept child view scroll
     */
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (totalY < 0 && myRecyclerView.isOrientation(0) || totalY > 0 && myRecyclerView.isOrientation(1)) {
            isfling = true;
        }
        if (IsRefresh) {
            if (dy > 0) {
                if (myRecyclerView.isOrientation(0)) {
                    totalY += dy;
                    if ((totalY / 2) <= 0) {
                        scrollTo(0, totalY / 2);
                        consumed[1] = dy;
                    } else {
                        scrollTo(0, 0);
                        consumed[1] = 0;
                    }
                }
                return;
            }
        }
        if (IsLoad) {
            if (dy < 0) {
                if (myRecyclerView.isOrientation(1)) {
                    totalY += dy;
                    if ((totalY / 2) >= 0) {
                        scrollTo(0, totalY / 2);
                        consumed[1] = dy;
                    } else {
                        scrollTo(0, 0);
                        consumed[1] = 0;
                    }
                }
                return;
            }
        }
    }

    /**
     * while child view move finish
     * dyUnconsumed Less than 0 move down
     * dyUnconsumed Major in 0 move up
     */
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed != 0) {
            totalY += dyUnconsumed;
            scrollTo(0, totalY / 2);
        }
    }

    public void onStopNestedScroll(View child) {
        helper.onStopNestedScroll(child);
        if (onTouchUpListener != null) {
            isfling = false;
            if (this.getTotal() >= headerHeight) {
                this.setScrollTo(this.getTotal(), headerHeight);
                if (!this.isScrollRefresh()) {
                    this.setIsScrollRefresh(true);
                    onTouchUpListener.OnRefreshing();
                }
            } else if (-this.getTotal() >= footerHeight) {
                this.setScrollTo(this.getTotal(), -footerHeight);
                if (!this.isScrollLoad()) {
                    this.setIsScrollLoad(true);
                    onTouchUpListener.OnLoading();
                }
            } else {
                this.setScrollTo(0, 0);
            }
        }
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return isfling;
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return isfling;
    }

    public int getNestedScrollAxes() {
        return helper.getNestedScrollAxes();
    }

    private void smoothScrollTo(float fromY, float toY) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{fromY, toY});
        if (fromY == toY) {
            animator.setDuration(0);
        } else {
            animator.setDuration(300);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int to = (int) (-((Float) animation.getAnimatedValue()).floatValue());
                scrollTo(0, to);
                totalY = to * 2;
            }
        });
        animator.start();
    }

}