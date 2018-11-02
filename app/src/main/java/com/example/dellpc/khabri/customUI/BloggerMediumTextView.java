package com.example.dellpc.khabri.customUI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BloggerMediumTextView extends android.support.v7.widget.AppCompatTextView{
    public BloggerMediumTextView(Context context) {
        super(context);
        initRegular();
    }

    public BloggerMediumTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRegular();
    }

    public BloggerMediumTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRegular();
    }

    private void initRegular()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Blogger-Sans-Medium.ttf");
            setTypeface(tf);
        }
    }

}
