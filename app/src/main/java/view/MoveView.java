package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class MoveView extends View {

    private Scroller scroller ;

    public MoveView(Context context) {
        this(context,null);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
    }


    int x;
    int y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                x = (int) event.getX();
                y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int mx = (int) (event.getX()-x);
                int my = (int) (event.getY()-y);

//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin += mx;
//                layoutParams.topMargin += my;

//                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                marginLayoutParams.leftMargin += mx;
//                marginLayoutParams.topMargin += my;
//                setLayoutParams(marginLayoutParams);

                //     layout(getLeft()+mx,getTop()+my,getRight()+mx,getBottom()+my);
   //             setX(mx+getX());
      //          setY(my+getY());
//                setTranslationX(mx+getTranslationX());
//                setTranslationY(my+getTranslationY());

         //       View view = (View) getParent();
           //     view.scrollBy(-mx,-my);
                scroller.startScroll(getScrollX(),getScrollY(),-mx,-my);
                invalidate();
                break;
        }

        return  true;
    }
    @Override
    public void computeScroll() {
        View view = (View) getParent();
        //重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (scroller.computeScrollOffset()) {
            view.scrollBy(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}
