package top.wgx.swprecyclerview.interfaces;

import java.util.List;

/**
 * Created by WU on 2017/5/12.
 */

public interface OnSelectListener<T> {
    List<Object> selectedData = null;
    List<T> getSelectedData();
    void setSelectedData(List<T> list);
    void addSelectedData(T t);
    void addSelectedData(List<T> mList);
    boolean isSelected(T t);
}
