package com.sample.studycustomview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomAnimNumberView extends View {
    private Paint paint;
    private int number;//
    private int numberColor;//文字颜色
    private int numberSize;//文字大小
    private int animDuration;//动画时长
    private ValueAnimator animation;//动画
    private Rect bounds;//文字边缘
    public CustomAnimNumberView(Context context) {
        this(context,null);
    }

    public CustomAnimNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomAnimNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomAnimNumberView,defStyleAttr,0);
        number = typedArray.getInt(R.styleable.CustomAnimNumberView_number,0);
        numberColor = typedArray.getColor(R.styleable.CustomAnimNumberView_numberColor, Color.BLACK);
        numberSize = typedArray.getDimensionPixelSize(R.styleable.CustomAnimNumberView_numberSize,FormatUtil.sp2px(context,18));
        animDuration = typedArray.getInt(R.styleable.CustomAnimNumberView_animDuration,1000);
        typedArray.recycle();
        paint = new Paint();
        paint.setTextSize(numberSize);
        paint.setColor(numberColor);
        bounds = new Rect();
        paint.getTextBounds(String.valueOf(number),0,String.valueOf(number).length(),bounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽高为固定dp 或 match_parent 直接使用以上获得的width和height即可，如果是wrap_content 需要单独处理
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST){
            width = getPaddingLeft() + getPaddingRight() + bounds.width();
        }
        if(heightMode == MeasureSpec.AT_MOST){
            height = getPaddingTop() + getPaddingBottom() + bounds.height();
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //传入左下角
        paint.getTextBounds(String.valueOf(number),0,String.valueOf(number).length(),bounds);
        canvas.drawText(String.valueOf(number),getPaddingLeft(),getPaddingTop()+bounds.height(),paint);
        if(animation == null){
            animation = ValueAnimator.ofInt(0,number);
            animation.setDuration(animDuration);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    number = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animation.start();
        }
    }
}
