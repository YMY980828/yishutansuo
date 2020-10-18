package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yishutansuo.R;


public class Compose extends RelativeLayout {


    public Compose(Context context) {
        super(context);
        init(context);

    }

    public Compose(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mColor = typedArray.getColor(R.styleable.TitleBar_bg_color,mColor);
        mTextColor = typedArray.getColor(R.styleable.TitleBar_bg_color,mTextColor);
        typedArray.recycle();

        init(context);
    }

    public Compose(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }
    private ImageView left,right;
    private TextView title;
    private RelativeLayout relativeLayout;
    private int mColor = Color.BLUE;
    private int mTextColor = Color.WHITE;
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_customtitle,this,true);
        left = findViewById(R.id.back);
        right = findViewById(R.id.right);
        title = findViewById(R.id.text);
        relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(mColor);
        title.setTextColor(mTextColor);

    }

    public void setTitle(String t){
        if (!TextUtils.isEmpty(t)){
            title.setText(t);
        }
    }

    public void setLeftListener(OnClickListener onClickListener){
        left.setOnClickListener(onClickListener);
    }
    public void setRightListener(OnClickListener onClickListener){
        right.setOnClickListener(onClickListener);
    }



}
