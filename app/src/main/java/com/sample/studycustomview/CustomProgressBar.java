package com.sample.studycustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgressBar extends ProgressBar{
    private Paint mPaint;
    private int mCircleColor;//圆的颜色
    private int mCircleWidth;//圆的粗细
    private int mStartAngle;//起始角度
    private int mTextSize;//文字大小
    private int mTextColor;//文字颜色
    private RectF mRectF;//限制弧线的矩形
    private Rect mBounds;//测量文字的边缘
    public CustomProgressBar(Context context) {
        this(context,null,0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomProgressBar,defStyleAttr,0);
        //获取圆的颜色，默认黑色
        mCircleColor = typedArray.getColor(R.styleable.CustomProgressBar_circleColor,Color.BLACK);
        //获取圆的粗细，默认5dp
        mCircleWidth = (int) typedArray.getDimension(R.styleable.CustomProgressBar_circleWidth,FormatUtil.dp2px(context,5));
        //获取圆的起始角度，默认0度
        mStartAngle = typedArray.getInteger(R.styleable.CustomProgressBar_startAngle,0);
        //获取文字大小，默认18sp
        mTextSize = (int) typedArray.getDimension(R.styleable.CustomProgressBar_textSize,FormatUtil.sp2px(getContext(),18));
        //获取文字颜色，默认黑色
        mTextColor = typedArray.getColor(R.styleable.CustomProgressBar_textColor,Color.BLACK);
        typedArray.recycle();
        mRectF = new RectF();
        mBounds = new Rect();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽高为固定dp 或 match_parent 直接使用以上获得的width和height即可，如果是wrap_content 需要单独处理
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //默认宽度60dp，默认高度60dp
        if(widthMode == MeasureSpec.AT_MOST){
            width = getPaddingLeft() + getPaddingRight() + FormatUtil.dp2px(getContext(),60);
        }
        if(heightMode == MeasureSpec.AT_MOST){
            height = getPaddingTop() + getPaddingBottom() + FormatUtil.dp2px(getContext(),60);
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //1.画圆弧
        mPaint.setAntiAlias(true);
        //设置只画边框模式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mCircleColor);
        mPaint.setStrokeWidth(mCircleWidth);
        //限制圆弧的左、上、右、下坐标
        mRectF.set(getPaddingLeft(),getPaddingTop(),getWidth() - getPaddingRight(),getHeight() - getPaddingBottom());
        //画圆弧，传入RectF，开始角度，扫过角度，是否连接中心，画笔
        canvas.drawArc(mRectF,mStartAngle,getProgress()*1.0f/getMax()*360,false,mPaint);
        //2.画文字
        String strProgress = getProgress()+"%";
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setStrokeWidth(FormatUtil.dp2px(getContext(),1));
        //设置填充模式
        mPaint.setStyle(Paint.Style.FILL);
        //获取文字边缘
        mPaint.getTextBounds(strProgress,0,strProgress.length(),mBounds);
        //画文字，传入文字内容，文字左下角坐标，画笔
        canvas.drawText(strProgress
                ,(getWidth() - getPaddingLeft() - getPaddingRight() - mBounds.width())/2+getPaddingLeft()
                ,(getHeight() - getPaddingTop() - getPaddingBottom() - mBounds.height())/2+getPaddingTop()+mBounds.height(),mPaint);
    }
}
