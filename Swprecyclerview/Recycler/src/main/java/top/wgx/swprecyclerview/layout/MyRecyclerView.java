package top.wgx.swprecyclerview.layout;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by WU on 2017/4/21.
 */

public  class MyRecyclerView extends RecyclerView {
    private StaggeredGridLayoutManager staggeredGridLayoutManager = null;
    private LinearLayoutManager linearLayoutManager = null;
    private GridLayoutManager gridLayoutManager = null;
    boolean isScrollLoad = false;
    boolean isScrollRefresh = false;

    public MyRecyclerView(Context context) {
        super(context);
        setVerticalFadingEdgeEnabled(false);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setItemAnimator(new DefaultItemAnimator());
    }

    void setMyLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        } else if (layoutManager instanceof GridLayoutManager) {
            gridLayoutManager = (GridLayoutManager) layoutManager;
        } else if (layoutManager instanceof LinearLayoutManager) {
            linearLayoutManager = (LinearLayoutManager) layoutManager;
        }
        setLayoutManager(layoutManager);
        if (!isVertical()) {
            throw new NullPointerException("vertical!");
        }
    } /**
     * orientation
     * 0 menas down
     * 1 means up
     */
    boolean isOrientation(int orientation) {
        if (orientation == 0)
            return isCanPullDown();
        else if (orientation == 1)
            return isCanPullUp();
        return false;
    }

    private boolean isCanPullDown() {
        return !canScrollVertically(-1);
    }

    private boolean isCanPullUp() {
        return !canScrollVertically(1);
    }

    private boolean isVertical() {
        if (staggeredGridLayoutManager != null)
            return staggeredGridLayoutManager.getOrientation() == StaggeredGridLayoutManager.VERTICAL;
        else if (linearLayoutManager != null)
            return linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
        else if (gridLayoutManager != null)
            return gridLayoutManager.getOrientation() == GridLayoutManager.VERTICAL;
        return false;
    }
}