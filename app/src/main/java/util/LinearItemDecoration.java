package util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    Paint paint;
    Paint maskPaint;
    public LinearItemDecoration(){
         paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    int color1 = Color.rgb(249,205,173);
    int color2 = Color.rgb(200,200,169);


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);


        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        for (int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);

            int left = manager.getLeftDecorationWidth(child);

            int cy = child.getTop()+child.getHeight()/2;
            c.drawCircle(left/2,cy,20,paint);
            c.drawLine(0, child.getTop()+child.getHeight(),child.getWidth()+left, child.getTop()+child.getHeight(),paint);
        }

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        LinearGradient linearGradient = new LinearGradient(parent.getWidth(),0,parent.getWidth(),200,color1,color2, Shader.TileMode.CLAMP);
        maskPaint.setShader(linearGradient);
        c.drawRect(0,0,parent.getWidth(),200,maskPaint);


    }

    /**
     *
     * @param outRect
     * @param view  当前view
     * @param parent RecyclerView本身
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left=100;
        outRect.bottom = 1;
    }



}
