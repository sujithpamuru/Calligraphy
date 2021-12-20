package io.github.inflationx.calligraphy3;

import ohos.agp.text.Font;
import ohos.global.resource.ResourceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * on pre-ICS versions.
 * More details can be found here https://code.google.com/p/android/issues/detail?id=9904
 * Created by Chris Jenkins on 04/09/13.
 */

public final class TypefaceUtils {

    private static final Map<String, Font> sCachedFonts = new HashMap<String, Font>();

    private static final Map<Font, CalligraphyTypefaceSpan> sCachedSpans = new HashMap<Font, CalligraphyTypefaceSpan>();

    /**
     * A helper loading custom spans so we don't have to keep creating hundreds of spans.
     *
     * @param typeface not null typeface
     * @return will return null of typeface passed in is null.
     */

    public static CalligraphyTypefaceSpan getSpan(final Font typeface) {
        if (typeface == null) {
            return null;
        }
        synchronized (sCachedSpans) {
            if (!sCachedSpans.containsKey(typeface)) {
                final CalligraphyTypefaceSpan span = new CalligraphyTypefaceSpan(typeface);
                sCachedSpans.put(typeface, span);
                return span;
            }
            return sCachedSpans.get(typeface);
        }
    }

    /**
     * Is the passed in typeface one of ours?
     *
     * @param typeface nullable, the typeface to check if ours.
     * @return true if we have loaded it false otherwise.
     */

    public static boolean isLoaded(Font typeface) {
        return typeface != null && sCachedFonts.containsValue(typeface);
    }

    private TypefaceUtils() {
    }
}