package top.wgx.swprecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import top.wgx.swprecyclerview.R;
import top.wgx.swprecyclerview.adapter.a.BaseRecyclerHolder;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class SimpleAdapter extends BRAdapter<String> {
    public SimpleAdapter() {
        super(android.R.layout.simple_list_item_1);
    }

    public SimpleAdapter(int mLyoutId) {
        super(mLyoutId);
    }

    @Override
    protected void bindData(Context context, BaseRecyclerHolder bHolder, int position, String s) {
        super.bindData(context, bHolder, position, s);
        bHolder.setText(android.R.id.text1,s);
    }
}
