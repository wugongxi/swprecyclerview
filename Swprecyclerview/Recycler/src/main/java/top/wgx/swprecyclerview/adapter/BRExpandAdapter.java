package top.wgx.swprecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

import top.wgx.swprecyclerview.adapter.a.BaseRecyclerHolder;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public abstract class BRExpandAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    private boolean mDefault = false;//默认关闭
    private static final int TYPE_GROUP = 1011;
    private static final int TYPE_ITEM = 1012;
    private List<T> mList = null;
    int layout_group = 0, layout_item = 0;
    private boolean[] misClose;

    public BRExpandAdapter() {
     init();
//        this.misClose = new boolean[0];
    }

    public BRExpandAdapter(int layout_group,int layout_item){
        this.layout_group = layout_group;
        this.layout_item = layout_item;
        init();
    }

    protected void init() {
        this.mList = new ArrayList<>();
    }

    public boolean getDefault() {
        return mDefault;
    }

    public void setmDefault(boolean mDefault) {
        this.mDefault = mDefault;
    }

    public void setList(List<T> list) {
        mList.clear();
        mList.addAll(list);
        misClose = new boolean[list.size()];
        for (int i = 0; i < mList.size(); i++) {
            misClose[i] = mDefault;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int[] groupAndChild = getGroupAndChild(position);
        if (groupAndChild[1]<0){
            return TYPE_GROUP;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GROUP) {
            return new GroupHolder(View.inflate(parent.getContext(), layout_group, null));
        } else{
            return new ChildHolder(View.inflate(parent.getContext(), layout_item, null));
        }
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {
        final int[] x = getGroupAndChild(position);
        if (getItemViewType(position) == TYPE_GROUP) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchState(x[0]);
                }
            });
            bindGroup(holder.itemView.getContext(), holder, mList.get(x[0]), x[0]);
        } else {
            bindChild(holder.itemView.getContext(), holder, mList.get(x[0]), x[0], x[1]);
        }
    }

    private void switchState(int i) {
        if (misClose[i]) {
            closeGroup(i);
        } else {
            expandGroup(i);
        }
    }

    public void expandGroup(int i) {
        misClose[i] = true;
        notifyDataSetChanged();
    }

    public void closeGroup(int i) {
        misClose[i] = false;
        notifyDataSetChanged();
    }


    private int[] getGroupAndChild(int position) {
        int[] x = new int[]{-1, -1};
        int count = -1;
        for (int i = 0; i < getGroupCount(); i++) {
            count++;
            x[0] = i;
            if (count == position) {
                return x;
            } else {
                if (misClose[x[0]]){
                    continue;
                }
                int childrenCount = getChildrenCount(mList.get(x[0]),i);
                if (count < position && position <= count + childrenCount) {
                    x[1] = position - count - 1;
                    return x;
                } else {
                    count += childrenCount;
                }
            }
        }
        return x;
    }


    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < mList.size(); i++) {
            count += (misClose[i] ? getChildrenCount(mList.get(i),i) : 0) + 1;
        }
        return count;
    }


    public int getGroupCount() {
        return mList.size();
    }
    protected abstract int getChildrenCount(T t,int group) ;
    protected abstract void bindGroup(Context context, BaseRecyclerHolder bHolder, T t, int group);
    protected abstract void bindChild(Context context, BaseRecyclerHolder bHolder,T t, int group, int child);




    class GroupHolder extends BaseRecyclerHolder {

        public GroupHolder(View itemView) {
            super(itemView);
        }
    }

    class ChildHolder extends BaseRecyclerHolder {

        public ChildHolder(View itemView) {
            super(itemView);
        }
    }

}
