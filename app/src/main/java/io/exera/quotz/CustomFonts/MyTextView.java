package io.exera.quotz.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;

import me.grantland.widget.AutofitTextView;

/**
 * Created by one on 3/12/15.
 */
public class MyTextView extends AutofitTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setMinTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        if (!isInEditMode()) {
            Typeface tf;
            if (getTypeface() == null) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_NORMAL));
            } else {
                switch (getTypeface().getStyle()) {
                    case Typeface.BOLD:
                        tf = Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_BOLD);
                        break;
                    case Typeface.ITALIC:
                        tf = Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_MEDIUM);
                        break;
                    default:
                        tf = Typeface.createFromAsset(getContext().getAssets(), FontConstants.FONT_NORMAL);
                }
                setTypeface(tf);
            }
        }
    }
}