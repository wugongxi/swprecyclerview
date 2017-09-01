package top.wgx.swprecyclerview.widget.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by WU on 2017/5/15.
 */

public class LoadView extends android.support.v7.widget.AppCompatImageView {
    private int velocity = 1;
    private Paint paint;
    private Bitmap bitmap;
    private Rect src;
    private Rect dst;
    int sw, sh, bw, bh;
    float srct = 0;
    float dstt = 0;
    float srcl = 0;
    float dstl = 0;
    float srcr = 0;
    float dstr = 0;
    float srcb = 0;
    float dstb = 0;
    float currprogress = 0;
    float toatal = 100;
    int direction = 0;
   private boolean isSingle = true;
    private long space = 16;

    private float auto = 0;
    private int autoMsg = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (auto > toatal) {
                auto = 0;
                if (!isSingle) {
                    if (onBitMapChanagedListener!=null) {
                        onBitMapChanagedListener.onChanaged(bitmap, direction);
                    }
                    setDirection(direction);
                    direction++;
                }
            }
            setCurrprogress(auto);
            auto+=velocity;
            handler.sendEmptyMessageDelayed(autoMsg, space);
            super.handleMessage(msg);
        }
    };

    public OnBitMapChanagedListener getOnBitMapChanagedListener() {
        return onBitMapChanagedListener;
    }

    public LoadView setOnBitMapChanagedListener(OnBitMapChanagedListener onBitMapChanagedListener) {
        this.onBitMapChanagedListener = onBitMapChanagedListener;
        return this;
    }

    private OnBitMapChanagedListener onBitMapChanagedListener;
    interface OnBitMapChanagedListener{
        Void onChanaged(Bitmap bitmap, int direction);
    }


    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
//        bitmap = Bitmap.create;
        src = new Rect();
        dst = new Rect();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public LoadView setChangeBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        bw = bitmap.getWidth();
        bh = bitmap.getHeight();
        return this;
    }

    public LoadView setBackGroundBitmap(Bitmap bitmap) {
        setBackGroundBitmap(bitmap);
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sw = w;
        sh = h;
    }

    public int getDirection() {
        return direction;
    }

    public LoadView setDirection(int isv) {
        this.direction = isv;
        invalidate();
        return this;
    }

    public LoadView setDefault(){
        auto = 0;
        currprogress = 0;
        toatal = 100;
        direction = 0;
        space = 15;
        velocity = 1;
        invalidate();
        if (handler!=null) {
            handler.removeMessages(0);
        }
        return this;
    }

    public float getCurrprogress() {
        return currprogress;
    }

    public LoadView setCurrprogress(float currprogress) {
        this.currprogress = currprogress;
        invalidate();
        return this;
    }

    public float getToatal() {
        return toatal;
    }

    public LoadView setToatal(float toatal) {
        this.toatal = toatal;
        invalidate();
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (direction %4) {
            case 0:
                srct = bh - bh / toatal * currprogress;
                dstt = sh - sh / toatal * currprogress;
                srcl = 0;
                dstl = 0;
                srcr = bw;
                srcb = bh;
                dstr = sw;
                dstb = sh;
                break;
            case 1:
                srct = 0;
                dstt = 0;
                srcl = bw - bw / toatal * currprogress;
                dstl = sw - sw / toatal * currprogress;
                srcr = bw;
                srcb = bh;
                dstr = sw;
                dstb = sh;
                break;
            case 2:
                srct = 0;
                dstt = 0;
                srcl = 0;
                dstl = 0;
                srcr = bw / toatal * currprogress;
                dstr = sw / toatal * currprogress;
                srcb = bh;
                dstb = sh;
                break;
            case 3:
                srct = 0;
                dstt = 0;
                srcl = 0;
                dstl = 0;
                srcr = bw;
                dstr = sw;
                srcb = bh / toatal * currprogress;
                dstb = sh / toatal * currprogress;
                break;
        }
        src.set((int) srcl, (int) srct, (int) srcr, (int) srcb);
        dst.set((int) dstl, (int) dstt, (int) dstr, (int) dstb);
        canvas.drawBitmap(bitmap, src, dst, paint);
    }

    public LoadView onstartAuto() {setToatal(100);
        if (handler != null) {
            handler.sendEmptyMessage(autoMsg);
        }
        return this;
    }

    public LoadView onstopAuto() {
        if (handler != null) {
            handler.removeMessages(autoMsg);
        }
        return this;
    }

    public int getVelocity() {
        return velocity;
    }

    public LoadView setVelocity(int velocity) {
        if (velocity<=0){
            velocity =1;
        }
        this.velocity = velocity;
        return this;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(autoMsg);
        handler = null;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public LoadView setSingle(boolean single) {
        isSingle = single;
        invalidate();
        return this;
    }

    public long getSpace() {
        return space;
    }

    public LoadView setSpace(long space) {
        this.space = space;
        invalidate();
        return this;
    }
}
