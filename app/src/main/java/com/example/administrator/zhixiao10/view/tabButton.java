package com.example.administrator.zhixiao10.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhixiao10.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class tabButton extends LinearLayout {

    private ImageView imageView;
    private TextView textView;
    private String btitle;
    private int titleColourDefault,titleColourSelect;
    private float textSize;
    private boolean isSelect = false;
    private int defaultImage,selectImage;




    public tabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public tabButton(Context context) {
        super(context);

    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_btn, this, true);
        textView = (TextView) view.findViewById(R.id.tv);
        imageView = (ImageView) view.findViewById(R.id.iv);
        // 获取所有自定义属性组
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.tabButton);
        // 获取自定义组件所在布局文件中的自定义属性值
        defaultImage = a.getResourceId(R.styleable.tabButton_defaultImage,0);
        selectImage = a.getResourceId(R.styleable.tabButton_selectImage,0);
        //设置值
        imageView.setImageDrawable(getResources().getDrawable(defaultImage));
        btitle = a.getString(R.styleable.tabButton_btitle);
        Log.i("nihao", "init: " +btitle);
        textView.setText(btitle);

        titleColourDefault = a.getColor(R.styleable.tabButton_titleColourDefault, getResources().getColor(R.color.colorAccent));
        titleColourSelect =  a.getColor(R.styleable.tabButton_titleColourSelect,getResources().getColor(R.color.colorAccent));
        textView.setTextColor(titleColourDefault);
        textSize = a.getDimension(R.styleable.tabButton_titleSize,10);
        textView.setTextSize(textSize);
        a.recycle();

    }


    public  void ChangeSelect(){
        isSelect = !isSelect;
        if(isSelect){
            imageView.setImageDrawable(getResources().getDrawable(selectImage));
            textView.setTextColor(titleColourSelect);
        }else {
            imageView.setImageDrawable(getResources().getDrawable(defaultImage));
            textView.setTextColor(titleColourDefault);
        }
    }

    public boolean getIsSelect(){
        return this.isSelect;
    }
    public void setIsSelect(boolean isSelect){
        if(isSelect){
            imageView.setImageDrawable(getResources().getDrawable(selectImage));
            textView.setTextColor(titleColourSelect);
        }else {
            imageView.setImageDrawable(getResources().getDrawable(defaultImage));
            textView.setTextColor(titleColourDefault);
        }
    }

}
