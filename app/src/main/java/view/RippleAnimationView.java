package view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.example.yishutansuo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RippleAnimationView extends RelativeLayout {
    Paint paint;
    private AnimatorSet animatorSet;
    private List<RippleCircleView> viewList = new ArrayList<>();
    private List<Animator> animators = new ArrayList<>();
    public RippleAnimationView(Context context) {
        super(context);
        init(context,null);
    }

    public RippleAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RippleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private int radius;
    private int strokeWidth;
    private int  rippleColor;
    private void init(Context context,AttributeSet attrs){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        int rippleType = typedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_type,0);
        radius = typedArray.getInt(R.styleable.RippleAnimationView_radius,54);
        strokeWidth = typedArray.getInt(R.styleable.RippleAnimationView_strokeWidth,2);
        rippleColor = typedArray.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(context,R.color.colorAccent));
        if (rippleType==0){
            paint.setStyle(Paint.Style.FILL);
        }else{
            paint.setStyle(Paint.Style.STROKE);
        }
        typedArray.recycle();
        paint.setColor(rippleColor);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;
        int d = Math.min(w,h);
        float maxScale = d/(2*radius);
        int duration = 3500;
        int singleDelay = duration/4;//间隔时间
        //实例化一个波纹
//        RippleCircleView rippleCircleView = new RippleCircleView(this);
        LayoutParams rippleParams = new LayoutParams(radius+strokeWidth,radius+strokeWidth);
        rippleParams.addRule(CENTER_IN_PARENT,TRUE);
        for (int i=0;i<4;i++){
            RippleCircleView rippleCircleView = new RippleCircleView(this);
            viewList.add(rippleCircleView);
            addView(rippleCircleView,rippleParams);
            //x
            final ObjectAnimator objectXAnimator =  ObjectAnimator.ofFloat(rippleCircleView,"scaleX",1.0f,maxScale);
            objectXAnimator.setRepeatCount(-1);
            objectXAnimator.setRepeatMode(ValueAnimator.RESTART);
          objectXAnimator.setStartDelay(i*singleDelay);
            objectXAnimator.setDuration(duration);
            animators.add(objectXAnimator);
            //y
            final ObjectAnimator objectYAnimator =  ObjectAnimator.ofFloat(rippleCircleView,"scaleY",1.0f,maxScale);
            objectYAnimator.setRepeatCount(-1);
            objectYAnimator.setRepeatMode(ValueAnimator.RESTART);
            objectYAnimator.setStartDelay(i*singleDelay);
            objectYAnimator.setDuration(duration);
            animators.add(objectYAnimator);
            //alpha
            final ObjectAnimator alpha =  ObjectAnimator.ofFloat(rippleCircleView,"alpha",1.0f,0f);
            alpha.setRepeatCount(-1);
            alpha.setRepeatMode(ValueAnimator.RESTART);
         alpha.setStartDelay(i*singleDelay);
            alpha.setDuration(duration);
            animators.add(alpha);
        }
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animators);
    }

    public boolean running =false;
    public void start(){
        if (!running){
            for (RippleCircleView r:viewList){
                r.setVisibility(VISIBLE);
            }
            animatorSet.start();
            running=true;
        }


    }

    public void stop(){
        if (running){
            Collections.reverse(viewList);
            for (RippleCircleView r:viewList){
                r.setVisibility(INVISIBLE);
            }
            animatorSet.end();
            running=false;
        }
    }


    public int getStokeWidth() {
        return strokeWidth;
    }

    public Paint getPaint() {
        return paint;
    }
}
