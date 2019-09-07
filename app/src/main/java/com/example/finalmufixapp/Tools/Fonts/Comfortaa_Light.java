package com.example.finalmufixapp.Tools.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Comfortaa_Light extends android.support.v7.widget.AppCompatTextView{
    public Comfortaa_Light(Context context) {
        super(context);
    }

    public Comfortaa_Light(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Comfortaa-Light.ttf"));


    }
}