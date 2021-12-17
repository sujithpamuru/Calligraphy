package io.github.inflationx.calligraphy3;

import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.agp.text.Font;
import ohos.agp.components.Text;
import ohos.agp.components.Text.TextObserver;
import ohos.agp.utils.TextTool;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;
import ohos.global.resource.solidxml.Pattern;
import ohos.global.resource.solidxml.SolidXml;
import ohos.global.resource.solidxml.TypedAttribute;
import ohos.agp.components.AttrSet;
//import com.hmos.compat.utils.AttrUtils;
import ohos.global.resource.solidxml.Theme;

import java.io.IOException;

/**
 * Created by chris on 20/12/2013
 * Project: Calligraphy
 */

public final class CalligraphyUtils {

    /**
     * Applies a Typeface to a TextView.
     * Defaults to false for deferring, if you are having issues with the textview keeping
     * the custom Typeface, use
     * @param textView Not null, TextView or child of.
     * @param typeface Not null, Typeface to apply to the TextView.
     * @return true if applied otherwise false.
     */

    public static boolean applyFontToTextView(final Text textView, final Font typeface) {
        return applyFontToTextView(textView, typeface, false);
    }

    /**
     * Applies a Typeface to a TextView, if deferred,its recommend you don't call this multiple
     * times, as this adds a TextWatcher.
     * <p>
     * Deferring should really only be used on tricky views which get Typeface set by the system at
     * weird times.
     *
     * @param textView Not null, TextView or child of.
     * @param typeface Not null, Typeface to apply to the TextView.
     * @param deferred If true we use Typefaces and TextChange listener to make sure font is always
     *                 applied, but this sometimes conflicts with other
     * @return true if applied otherwise false.
     */

    public static boolean applyFontToTextView(final Text textView, final Font typeface, boolean deferred) {
        if (textView == null || typeface == null) {
            return false;
        }
        textView.setFont(typeface);
        return true;
    }

    public int settingcolor = Color.BLACK.getValue();

    public int getColor(){
        int color = settingcolor;
        return color;
    }

    /**
     * Useful for manually fonts to a TextView. Will not default back to font
     * set in {@link CalligraphyConfig}
     *
     * @param context  Context
     * @param textView Not null, TextView to apply to.
     * @param filePath if null/empty will do nothing.
     * @return true if fonts been applied
     */

    public static boolean applyFontToTextView(final Context context, final Text textView, final String filePath) {
        return applyFontToTextView(context, textView, filePath, false);
    }

    static boolean applyFontToTextView(final Context context, final Text textView, final String filePath, boolean deferred) {
        if (textView == null || context == null) {
            return false;
        }
        final ResourceManager assetManager = (ResourceManager) context;
        final Font typeface = TypefaceUtils.load(assetManager, filePath);
        return applyFontToTextView(textView, typeface, deferred);
    }

    static void applyFontToTextView(final Context context, final Text textView, final CalligraphyConfig config) {
        applyFontToTextView(context, textView, config, false);
    }

    static void applyFontToTextView(final Context context, final Text textView, final CalligraphyConfig config, boolean deferred) {
        if (context == null || textView == null || config == null) {
            return;
        }
        if (!config.isFontSet()) {
            return;
        }
        applyFontToTextView(context, textView, config.getFontPath(), deferred);
    }

    /**
     * Applies font to TextView. Will fall back to the default one if not set.
     *
     * @param context      context
     * @param textView     textView to apply to.
     * @param config       Default Config
     * @param textViewFont nullable, will use Default Config if null or fails to find the
     *                     defined font.
     */

    public static void applyFontToTextView(final Context context, final Text textView, final CalligraphyConfig config, final String textViewFont) {
        applyFontToTextView(context, textView, config, textViewFont, false);
    }

    static void applyFontToTextView(final Context context, final Text textView, final CalligraphyConfig config, final String textViewFont, boolean deferred) {
        if (context == null || textView == null || config == null) {
            return;
        }
        if (!TextTool.isNullOrEmpty(textViewFont) && applyFontToTextView(context, textView, textViewFont, deferred)) {
            return;
        }
        applyFontToTextView(context, textView, config, deferred);
    }

    /**
     * Tries to pull the Font Path from the View Style as this is the next decendent after being
     * defined in the View's xml.
     *
     * @param context     Activity Activity Context
     * @param attrs       View Attributes
     * @param attributeId if -1 returns null.
     * @return null if attribute is not defined or found in the Style
     */

    static String pullFontPathFromStyle(Context context, AttrSet attrs, int[] attributeId) {
        if (attributeId == null || attrs == null) {
            return null;
        }
        return null;
    }

    /**
     * Tries to pull the Font Path from the Text Appearance.
     *
     * @param context     Activity Context
     * @param attrs       View Attributes
     * @param attributeId if -1 returns null.
     * @return returns null if attribute is not defined or if no TextAppearance is found.
     */

    static String pullFontPathFromTextAppearance(final Context context, AttrSet attrs, int[] attributeId) {
        if (attributeId == null || attrs == null) {
            return null;
        }
        int textAppearanceId = -1;
        //final Theme textAppearanceAttrs = context.obtainStyledAttributes(textAppearanceId, attributeId);
        return null;
    }

    /**
     * Last but not least, try to pull the Font Path from the Theme, which is defined.
     *
     * @param context     Activity Context
     * @param styleAttrId Theme style id
     * @param attributeId if -1 returns null.
     * @return null if no theme or attribute defined.
     */

    static String pullFontPathFromTheme(Context context, int styleAttrId, int[] attributeId) {
        if (styleAttrId == -1 || attributeId == null) {
            return null;
        }
        final Theme theme = context.getTheme();
        final TypedAttribute value = new TypedAttribute() {
            @Override
            public int getType() {
                return 0;
            }

            @Override
            public boolean getBooleanValue() throws IOException, NotExistException, WrongTypeException {
                return false;
            }

            @Override
            public int getColorValue() throws IOException, NotExistException, WrongTypeException {
                return 0;
            }

            @Override
            public float getFloatValue() throws IOException, NotExistException, WrongTypeException {
                return 0;
            }

            @Override
            public int getIntegerValue() throws IOException, NotExistException, WrongTypeException {
                return 0;
            }

            @Override
            public String getStringValue() throws IOException, NotExistException, WrongTypeException {
                return null;
            }

            @Override
            public SolidXml getLayoutValue() throws IOException, NotExistException, WrongTypeException {
                return null;
            }

            @Override
            public String getMediaValue() throws IOException, NotExistException, WrongTypeException {
                return null;
            }

            @Override
            public String getOriginalValue() {
                return null;
            }

            @Override
            public Pattern getPatternValue() throws IOException, NotExistException, WrongTypeException {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getResId() throws IOException, NotExistException, WrongTypeException {
                return 0;
            }

            @Override
            public String getParsedValue() throws IOException, NotExistException, WrongTypeException {
                return null;
            }

            @Override
            public ResourceManager getResourceManager() {
                return null;
            }

            @Override
            public Resource getMediaResource() throws IOException, NotExistException, WrongTypeException {
                return null;
            }

            @Override
            public int getPixelValue(boolean b) throws IOException, NotExistException, WrongTypeException {
                return 0;
            }
        };

        return theme.toString();
    }

    /**
     * Last but not least, try to pull the Font Path from the Theme, which is defined.
     *
     * @param context        Activity Context
     * @param styleAttrId    Theme style id
     * @param subStyleAttrId the sub style from the theme to look up after the first style
     * @param attributeId    if -1 returns null.
     * @return null if no theme or attribute defined.
     */

    static String pullFontPathFromTheme(Context context, int styleAttrId, int subStyleAttrId, int[] attributeId) {
        if (styleAttrId == -1 || attributeId == null) {
            return null;
        }
        final Theme theme = context.getTheme();
        return theme.toString();
    }

    private static Boolean sToolbarCheck = null;

    private static Boolean sAppCompatViewCheck = null;

    /**
     * See if the user has added appcompat-v7, this is done at runtime, so we only check once.
     *
     * @return true if the v7.Toolbar is on the classpath
     */

    static boolean canCheckForV7Toolbar() {
        if (sToolbarCheck == null) {
            try {
                Class.forName("androidx.appcompat.widget.Toolbar");
                sToolbarCheck = Boolean.TRUE;
            } catch (ClassNotFoundException e) {
                sToolbarCheck = Boolean.FALSE;
            }
        }
        return sToolbarCheck;
    }

    /**
     * See if the user has added appcompat-v7 with AppCompatViews
     *
     * @return true if AppcompatTextView is on the classpath
     */

    static boolean canAddV7AppCompatViews() {
        if (sAppCompatViewCheck == null) {
            try {
                Class.forName("androidx.appcompat.widget.AppCompatTextView");
                sAppCompatViewCheck = Boolean.TRUE;
            } catch (ClassNotFoundException e) {
                sAppCompatViewCheck = Boolean.FALSE;
            }
        }
        return sAppCompatViewCheck;
    }

    public CalligraphyUtils() {
    }

    public static String changeParamToString(CharSequence charSequence) {
        String convertToString = charSequence.toString();
        return convertToString;
    }

    public static String pullFontPathFromView(Context context, AttrSet attrs, int[] mAttributeId) {
        context.toString();
        return null;
    }
}