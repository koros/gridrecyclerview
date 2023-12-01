package com.korosmatick.sampleapp.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.korosmatick.sampleapp.R;

public class ImageUtils {
    public static Drawable getDrawableFromName(Context context, String resourceName) {
        try {
            int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getApplicationContext().getPackageName());
            return context.getResources().getDrawable(resourceId);
        } catch (Exception e) {
            return context.getResources().getDrawable(R.drawable.ic_placeholder);
        }
    }
}
