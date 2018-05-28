package com.sample.studycustomview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomTitleView extends ConstraintLayout{
    private ConstraintLayout clTitleView;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivMenu;
    //背景色
    private int backgroundColor;
    //标题
    private String title;
    //菜单图片资源
    private int menuSrc;
    public CustomTitleView(Context context) {
        this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //绑定布局
        LayoutInflater.from(context).inflate(R.layout.title_view,this);
        //找到控件
        clTitleView = findViewById(R.id.title_view);
        ivBack = findViewById(R.id.back);
        tvTitle = findViewById(R.id.title);
        ivMenu = findViewById(R.id.menu);

        //获取属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomTitleView,defStyleAttr,0);
        //获取背景色属性，默认透明
        backgroundColor = typedArray.getColor(R.styleable.CustomTitleView_backgroundColor, Color.TRANSPARENT);
        //获取标题属性
        title = typedArray.getString(R.styleable.CustomTitleView_title);
        //获取菜单图片资源属性，未设置菜单图片资源则默认为-1，后面通过判断此值是否为-1决定是否设置图片
        menuSrc = typedArray.getResourceId(R.styleable.CustomTitleView_menuSrc,-1);
        //TypedArray使用完后需手动回收
        typedArray.recycle();

        //设置属性
        clTitleView.setBackgroundColor(backgroundColor);
        tvTitle.setText(title);
        if(menuSrc!=-1){
            ivMenu.setImageResource(menuSrc);
        }
        //back图标点击事件，点击关闭activity
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }
}
