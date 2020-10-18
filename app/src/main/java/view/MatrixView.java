package view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MatrixView extends View {
    public MatrixView(Context context) {
        super(context);
        init();
    }
    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    Paint mPaint;
    ColorMatrix colorMatrix;
    public void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //R G B A 增加的分量


    }
    private float ratio = 1;

    public void  animating(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,0.8f);
        valueAnimator.setDuration(8000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                ratio = 1-value;
                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 255
     * 0
     * 0
     * 255
     * 1
     *
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        colorMatrix = new ColorMatrix(new float[]{

                1,0,0,0,100,
                0,1,0,0,0,
                0,0,1,0,0,
                0,0,0,ratio,0

        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200,200,100,mPaint);
    }
}
