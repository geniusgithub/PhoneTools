
package com.geniusgithub.phonetools.util;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class ViewUtil {
    private static final String TAG = ViewUtil.class.getSimpleName();
   
    public static boolean isVisible(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }
    
    private static final ViewOutlineProvider OVAL_OUTLINE_PROVIDER = new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }

    };

    public static void setupFloatingActionButton(View view, float size) {
        view.setOutlineProvider(OVAL_OUTLINE_PROVIDER);
        view.setTranslationZ(size);
    }


    public static boolean isTextPhotoOn(){
    	return false;
    }
}
