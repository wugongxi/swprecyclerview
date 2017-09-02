package top.wgx.swprecyclerview.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.wgx.swprecyclerview.adapter.a.BaseRecyclerHolder;

/**
 * Created by BM-WGX on 2016/12/19.
 * 支持添加headerView 、footView
 * 删除指定条目
 * 增加指定条目
 * 增加头部条目
 * 增加尾部条目
 * 条目长按事件分发
 * 条目点击事件分发
 * <p>
 * 2017-04-24更新
 * 支持debug
 * this.setDebug(true);
 */

public class BRAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected static final int sHEADERVIEW = 0x001000;
    protected static final int sITEMVIEW = 0x001001;
    protected static final int sFOOTVIEW = 0x001002;
    protected View mHeaderView;
    protected View mFootView;
    protected int mHeaderViewid;
    protected int mFootViewId;
    protected List<T> mList;
    protected int mItemViewId;
    protected View mItemView;
    protected int select_position =0;
    protected boolean isDebug = false;

    public BRAdapter() {
        mList = new ArrayList<>();
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        mList.addAll(list);
    }


    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public View getFootView() {
        return mFootView;
    }

    public void setFootView(View footView) {
        mFootView = footView;
    }

    public int getHeaderViewid() {
        return mHeaderViewid;
    }

    public int getFootViewId() {
        return mFootViewId;
    }

    public View getItemView() {
        return mItemView;
    }

    public void setItemView(View itemView) {
        mItemView = itemView;
    }

    public int getItemViewId() {
        return mItemViewId;
    }

    public BRAdapter(int mLyoutId) {
        mList = new ArrayList<>();
        this.mItemViewId = mLyoutId;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return sHEADERVIEW;
        } else if (mFootView != null && position == getItemCount() - 1) {
            return sFOOTVIEW;
        } else {
            return sITEMVIEW;
        }
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == sITEMVIEW) {
            if (mItemView != null) {
                return new itemHolder(mItemView);
            } else {
                return new itemHolder(View.inflate(parent.getContext(), mItemViewId, null));
            }
        } else if (viewType == sHEADERVIEW) {
            if (mHeaderView != null) {
                return new HeaderHolder(mHeaderView);
            } else {
                return new HeaderHolder(View.inflate(parent.getContext(), mHeaderViewid, null));
            }

        } else if (viewType == sFOOTVIEW) {
            if (mFootView != null) {
                return new FootHolder(mFootView);
            } else {
                return new FootHolder(View.inflate(parent.getContext(), mFootViewId, null));
            }
        } else {
            return null;
        }

    }


    private View.OnLongClickListener listener = null;

    public void addOnLongClickListener(View.OnLongClickListener listener) {
        this.listener = listener;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {

        if (onItemClickListener != null) {
            select_position = position;
            holder.itemView.setOnClickListener(null);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemclick(v, mList, position);
                }
            });
            notifyDataSetChanged();
        }
        if (onItemLongClickListener != null) {
            select_position = position;
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onItemLongclick(v, mList, position);
                }
            });
            notifyDataSetChanged();
        }
        if (isDebug) {
            return;
        }
        if (mHeaderView != null && position == 0) {
            bindHeaderData(holder);
        } else if (mFootView != null && position == getItemCount() - 1) {
            bindFootData(holder);
        } else {
            if (mHeaderView != null) {
                bindData(holder.itemView.getContext(), holder, position, mList.get(position - 1));
                bindData( holder, position, mList.get(position - 1));
            } else {
                bindData(holder.itemView.getContext(), holder, position, mList.get(position));
                bindData(holder, position, mList.get(position));
            }
        }
    }

    protected void bindData(BaseRecyclerHolder holder, int position, T t) {

    }

    protected void bindData(Context context, RecyclerView.ViewHolder holder, int position, T t) {

    }

    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    protected void bindFootData(RecyclerView.ViewHolder holder) {

    }

    protected void bindHeaderData(RecyclerView.ViewHolder holder) {

    }


    public int getSelect_position() {
        return select_position;
    }

    @Override
    public int getItemCount() {
        return mList.size() + ((mHeaderView != null) ? 1 : 0) + ((mFootView != null) ? 1 : 0);
    }

    class HeaderHolder extends BaseRecyclerHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class itemHolder extends BaseRecyclerHolder {
        public itemHolder(View itemView) {
            super(itemView);
        }
    }

    class FootHolder extends BaseRecyclerHolder {
        public FootHolder(View itemView) {
            super(itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemclick(View v, List<T> mList, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener<T> {
        boolean onItemLongclick(View v, List<T> mList, int position);
    }
}