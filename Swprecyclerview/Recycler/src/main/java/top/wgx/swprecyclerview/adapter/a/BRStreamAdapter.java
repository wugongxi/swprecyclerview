package top.wgx.swprecyclerview.adapter.a;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WU on 2017/5/24.
 */

public class BRStreamAdapter<VH> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    List<View> views;
    public BRStreamAdapter() {
        this.views = new ArrayList<>();
    }

    public BRStreamAdapter addView(View view) {
        if (view != null) {
            views.add(view);
        }
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BaseRecyclerHolder(views.get(viewType));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (initViewimpl!=null){
            initViewimpl.initView(holder,position);
        }
        initView(holder,position);
    }

    private void initView(BaseRecyclerHolder holder, int position) {

    }

    public BRStreamAdapter.initViewimpl getInitViewimpl() {
        return initViewimpl;
    }

    public BRStreamAdapter setInitViewimpl(BRStreamAdapter.initViewimpl initViewimpl) {
        this.initViewimpl = initViewimpl;
        return this;
    }

    @Override
    public int getItemCount() {
        return views.size();
    }

    initViewimpl initViewimpl;

    interface initViewimpl{
       void initView(BaseRecyclerHolder holder, int viewType);
    }
}
