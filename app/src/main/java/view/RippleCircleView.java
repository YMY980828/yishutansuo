package view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.security.PrivateKey;

public class RippleCircleView extends View {
    private WeakReference<RippleAnimationView> rippleAnimationViewWeakReference;
    public RippleCircleView(RippleAnimationView rippleAnimationView){
        super(rippleAnimationView.getContext());
        rippleAnimationViewWeakReference = new WeakReference<>(rippleAnimationView);

    }
    public RippleCircleView(Context context) {
        super(context);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = Math.min(getWidth(),getHeight())/2;
        canvas.drawCircle(radius,radius,radius-rippleAnimationViewWeakReference.get().getStokeWidth(),rippleAnimationViewWeakReference.get().getPaint());
    }
}
