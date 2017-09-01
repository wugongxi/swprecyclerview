package top.wgx.swprecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BM-WGX on 2016/12/19.
 * 支持添加headerView 、footView
 */

public class BRMUAdapter<T> extends RecyclerView.Adapter {
    private static final int sHEADERVIEW = 0x000000;
    private static final int sITEMVIEW = 0x000001;
    private static final int sFOOTVIEW = 0x000002;

    protected List<T> mList;
    protected Context mContext;
    protected int mItemViewId;
    protected View mItemView;
    private int select_position = -1;

    public BRMUAdapter(Context context) {
        mContext = context;
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

    ArrayList<View> mHeader = null;

    public void addHeader(View headerView) {
        mHeader.add(headerView);
    }

    ArrayList<View> mFooter = null;

    public void addFooter(View mFootView) {
        mFooter.add(mFootView);
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

    public void setItemView(int layout) {
        mItemViewId = layout;
        mItemView = View.inflate(mContext, layout, null);
    }

    public BRMUAdapter(Context context, int mLyoutId) {
        mContext = context;
        mList = new ArrayList<>();
        this.mItemViewId = mLyoutId;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != null && position < mHeader.size()) {
            return position;
        } else if (mFooter != null && position < getItemCount() - mFooter.size()) {
            return position;
        } else {
            return sITEMVIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == sITEMVIEW) {
            if (mItemView != null) {
                return new itemHolder(mItemView, viewType);
            } else {
                return new itemHolder(View.inflate(mContext, mItemViewId, null), viewType);
            }
        } else if (mHeader != null) {
            return new HeaderHolder(mHeader.get(viewType), viewType);
        } else if (mFooter != null) {
            return new FootHolder(mFooter.get(getItemCount() - viewType - 1), viewType);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_position = position;
                    onItemClickListener.onItemclick(mList, position);
                    holder.itemView.setSelected(true);
                    notifyDataSetChanged();
                }
            });
        }

        if (mHeader != null && position == 0) {
            bindHeaderDate(holder);
        } else if (mFooter != null && position == getItemCount() - 1) {
            bindFootDate(holder);
        } else {
            if (mHeader != null) {
                bindDate(holder, position, mList.get(position - mHeader.size()));
            } else {
                bindDate(holder, position, mList.get(position));
            }
        }
    }

    protected void bindDate(RecyclerView.ViewHolder holder, int position, T t) {

    }

    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    protected void bindFootDate(RecyclerView.ViewHolder holder) {

    }

    protected void bindHeaderDate(RecyclerView.ViewHolder holder) {

    }


    public int getSelect_position() {
        return select_position;
    }

    @Override
    public int getItemCount() {
        return mList.size() + ((mHeader != null) ? mHeader.size() : 0) + ((mFooter!= null) ? mFooter.size() : 0);
    }

    class BaseRecyclerHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerHolder(View itemView, int type) {
            super(itemView);
            initView(itemView, type);
        }
    }

    public void initView(View itemView, int type) {

    }

    class HeaderHolder extends BaseRecyclerHolder {
        public HeaderHolder(View itemView, int type) {
            super(itemView, type);
        }
    }

    class itemHolder extends BaseRecyclerHolder {
        public itemHolder(View itemView, int type) {
            super(itemView, type);
        }
    }

    class FootHolder extends BaseRecyclerHolder {
        public FootHolder(View itemView, int type) {
            super(itemView, type);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemclick(List<T> mList, int position);
    }
}