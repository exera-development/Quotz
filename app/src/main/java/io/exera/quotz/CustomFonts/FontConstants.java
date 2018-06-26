package io.exera.quotz.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Attila on 3/5/2017.
 */

public class FontConstants
{

    public static final String FONT_BOLD = "MavenPro-Bold.ttf";
    public static final String FONT_NORMAL = "MavenPro-Regular.ttf";
    public static final String FONT_MEDIUM = "MavenPro-Medium.ttf";

    public static Typeface getFontNormal(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), FontConstants.FONT_NORMAL);
    }

    public static Typeface getFontBold(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), FontConstants.FONT_BOLD);
    }

    public static Typeface getFontMedium(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(), FontConstants.FONT_MEDIUM);
    }
}
