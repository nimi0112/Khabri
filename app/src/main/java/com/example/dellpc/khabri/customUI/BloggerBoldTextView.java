package com.example.dellpc.khabri.customUI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.dellpc.khabri.R;

public class BloggerBoldTextView extends android.support.v7.widget.AppCompatTextView{
    public BloggerBoldTextView(Context context) {
        super(context);
        initRegular();
    }

    public BloggerBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRegular();
    }

    public BloggerBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRegular();
    }

    private void initRegular()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Blogger-Sans-Bold.ttf");
            setTypeface(tf);
        }
    }


}
