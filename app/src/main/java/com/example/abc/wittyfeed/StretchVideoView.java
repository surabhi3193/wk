package com.example.abc.wittyfeed;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.VideoView;

/**
 * Created by abc on 15/04/2018.
 */

public class StretchVideoView extends VideoView {
    Context context;
    public StretchVideoView(Context context) {
        super(context);
        this.context=context;
    }

    public StretchVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public StretchVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.i("@@@@", "onMeasure");


        int mwidth = context.getResources().getDisplayMetrics().widthPixels + 50;


        int width = getDefaultSize(mwidth, widthMeasureSpec);
        int height = getDefaultSize(550, heightMeasureSpec);
        if (mwidth > 0) {
            if ( mwidth * height  > width * 550 ) {
                //Log.i("@@@", "image too tall, correcting");
                height = width * 550 / mwidth;
            } else if ( mwidth * height  < width * 550 ) {
                //Log.i("@@@", "image too wide, correcting");
                width = height * mwidth / 550;
            } else {
                //Log.i("@@@", "aspect ratio is correct: " +
                //width+"/"+height+"="+
                //550+"/"+550);
            }
        }
        //Log.i("@@@@@@@@@@", "setting size: " + width + 'x' + height);
        setMeasuredDimension(width, height);
    }
}