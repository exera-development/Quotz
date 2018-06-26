package io.exera.quotz.CustomFonts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by one on 3/12/15.
 */
public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf;
            if (getTypeface()==null) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_NORMAL));
            } else {
                switch (getTypeface().getStyle()) {
                    case Typeface.BOLD:
                        tf = Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_BOLD);
                        break;
                    default:
                        tf = Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_NORMAL);
                }
                setTypeface(tf);
            }
        }
    }

}