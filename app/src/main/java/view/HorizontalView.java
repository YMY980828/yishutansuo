package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class HorizontalView extends ViewGroup {


    public HorizontalView(Context context) {
        super(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getMode(heightMeasureSpec);




    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
