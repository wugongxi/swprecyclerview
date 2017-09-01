package top.wgx.swprecyclerview.adapter.a;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Angel on 2017/4/7.
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewSparseArray;

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public BaseRecyclerHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value!=null?value:"");
        return this;
    }

    public BaseRecyclerHolder setText(int viewId, CharSequence value) {
        TextView view = findViewById(viewId);
        view.setText(value!=null?value:"");
        return this;
    }
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = findViewById(viewId);
        if (bitmap!=null) {
            view.setImageBitmap(bitmap);
        }
        return this;
    }
    public BaseRecyclerHolder setVisible(int viewId, boolean visible) {
        View view = findViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }
    public BaseRecyclerHolder setGone(int viewId, boolean visible) {
        View view = findViewById(viewId);
        view.setVisibility(visible ? View.GONE : View.VISIBLE);
        return this;
    }
    public BaseRecyclerHolder setOnclick(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    public BaseRecyclerHolder setLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = findViewById(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
    public BaseRecyclerHolder setAlpha(int viewId, int a) {
        View view = findViewById(viewId);
        view.setAlpha(a);
        return this;
    }
    public BaseRecyclerHolder setAlpha(int a) {
        itemView.setAlpha(a);
        return this;
    }
    public BaseRecyclerHolder setAnimation(int viewId, Animation a) {
        View view = findViewById(viewId);
        view.setAnimation(a);
        return this;
    }
    public BaseRecyclerHolder setAnimation(Animation a) {;
        if (a!=null) {
            itemView.setAnimation(a);
        }
        return this;
    }
    public BaseRecyclerHolder linkify(int viewId) {
        TextView view = findViewById(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }
    public BaseRecyclerHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        CheckBox view = findViewById(viewId);
        view.setOnCheckedChangeListener(checkedChangeListener);
        return this;
    }
    public BaseRecyclerHolder setChecked(int viewId, boolean ischeck) {
        CheckBox view = findViewById(viewId);
        view.setChecked(ischeck);
        return this;
    }

}
