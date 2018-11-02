package com.example.dellpc.khabri.customUI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class MyRobotoRegularTextView extends android.support.v7.widget.AppCompatTextView{
    public MyRobotoRegularTextView(Context context) {
        super(context);
        initRegular();
    }

    public MyRobotoRegularTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRegular();
    }

    public MyRobotoRegularTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRegular();
    }

    private void initRegular()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto-condensed.regular.ttf");
            setTypeface(tf);
        }
    }

}
