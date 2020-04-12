package com.example.finalmufixapp.Tools.Fonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Comfortaa_Bold extends android.support.v7.widget.AppCompatTextView {
    public Comfortaa_Bold(Context context) {
        super(context);
    }

    public Comfortaa_Bold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Comfortaa-Bold.ttf"));


    }
}