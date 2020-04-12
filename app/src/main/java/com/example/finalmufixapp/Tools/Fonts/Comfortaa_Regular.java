package com.example.finalmufixapp.Tools.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Comfortaa_Regular extends android.support.v7.widget.AppCompatEditText{
    public Comfortaa_Regular(Context context) {
        super(context);
    }

    public Comfortaa_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Comfortaa-Regular.ttf"));


    }
}