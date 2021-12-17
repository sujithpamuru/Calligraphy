package io.github.inflationx.calligraphy3;

import ohos.agp.render.Paint;
import ohos.agp.text.Font;

public class CalligraphyTypefaceSpan {

    private final Font typeface;

    public CalligraphyTypefaceSpan(final Font typeface) {
        if (typeface == null) {
            throw new IllegalArgumentException("typeface is null");
        }
        this.typeface = typeface;
    }

    public void updateDrawState(final Paint drawState) {
        apply(drawState);
    }

    public void updateMeasureState(final Paint paint) {
        apply(paint);
    }

    private void apply(final Paint paint) {
        final Font oldTypeface = paint.getFont();
        final int oldStyle = oldTypeface != null ? Integer.parseInt(oldTypeface.getName()) : 0;
        final int fakeStyle = oldStyle & ~Integer.parseInt(typeface.getName());
        if ((fakeStyle & Font.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }
        paint.setFont(typeface);
    }
}
