package io.github.inflationx.calligraphy3;

import ohos.agp.text.Font;

/**
 * CalligraphyTypefaceSpan.
 */

public class CalligraphyTypefaceSpan {

    private final Font typeface;

    /**
     * CalligraphyTypefaceSpan.
     *
     * @param typeface typeface
     */

    public CalligraphyTypefaceSpan(final Font typeface) {
        if (typeface == null) {
            throw new IllegalArgumentException("typeface is null");
        }
        this.typeface = typeface;
    }
}
