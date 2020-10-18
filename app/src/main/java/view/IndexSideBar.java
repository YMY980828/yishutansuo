package view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IndexSideBar extends View {

    public static String[] b = {"#","A", "B", "C", "D", "E", "F", "G", "H", "I","J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z"};
    private Paint mPaint;
    private Paint sPaint;
    private int selectedIndex=0;
    public IndexSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint.setColor(Color.RED);
        mPaint.setColor(Color.parseColor("#888888"));
        mPaint.setAntiAlias(true);


    }
    private float ch=0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float h = getHeight();
        ch = h/b.length;
        for (int i=0;i<b.length;i++){
            if (i==selectedIndex){
                canvas.drawText(b[i],0,i*ch+(ch/2),sPaint);
            }else{
                canvas.drawText(b[i],0,i*ch+(ch/2),mPaint);
            }
        }

    }

    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    startY = event.getY();
                    selectedIndex= (int) (startY/ch);
                    if (selectedIndex>b.length-1){
                        selectedIndex=b.length-1;
                    }else if (selectedIndex<0){
                        selectedIndex=0;
                    }
                    mIndicatorTv.setY(selectedIndex*ch+(ch/2));
                  mIndicatorTv.setText(b[selectedIndex]);
                mIndicatorTv.setText(b[selectedIndex]);
                break;

        }
        invalidate();
        return  true;
    }
    private TextView mIndicatorTv;
    public void setIndicatorTv(TextView tv){
        mIndicatorTv = tv;
        mIndicatorTv.setVisibility(VISIBLE);
    }



}