package top.wgx.swprecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import top.wgx.swprecyclerview.interfaces.OnSelectListener;

/**
 * Created by WU on 2017/5/12.
 */

public class SelectAdapter<T> extends BRMUAdapter<T> implements OnSelectListener<T>{
    public SelectAdapter(Context context, int mLyoutId) {
        super(context, mLyoutId);
    }

    public void selected(T t) {
        if (isSelected(t)){
        selectedData.add(t);
        }

    }

    public void unSelected(T t) {
        if (isSelected(t)){
            selectedData.remove(t);
        }
    }

    @Override
    public List<T> getSelectedData() {
        return (List<T>) selectedData;
    }

    @Override
    public void setSelectedData(List<T> list) {
        selectedData.clear();
        selectedData.addAll(list);
    }

    @Override
    public void addSelectedData(T t) {
        selectedData.add(t);
    }

    @Override
    public void addSelectedData(List<T> mList) {
        selectedData.addAll(mList);
    }

    @Override
    public boolean isSelected(T t) {
        for (Object o: selectedData) {
            if (compare((T) o,t)){
                return true;
            }
        }
        return false;
    }

    //
    private boolean compare(T o, T t) {
        return false;
    }

}
