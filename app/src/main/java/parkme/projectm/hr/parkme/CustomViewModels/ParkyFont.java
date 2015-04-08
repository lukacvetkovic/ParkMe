package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Mihael on 5.4.2015..
 */
public class ParkyFont {

    private static ParkyFont parkyFontInstance;
    private static Typeface parkyTypeface;

    private static String fontAssetsPath = "HUSSARBOLD.ttf";

    private ParkyFont() {
    }

    public static ParkyFont getInstance() {
        if(parkyFontInstance == null){
            parkyFontInstance = new ParkyFont();
        }
        return parkyFontInstance;
    }

    public Typeface getParkyTypeface(Context context){
        if(parkyTypeface == null) {
            parkyTypeface = Typeface.createFromAsset(context.getAssets(), fontAssetsPath);
        }
        return parkyTypeface;
    }

}
