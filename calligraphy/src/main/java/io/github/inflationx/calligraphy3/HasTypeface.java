package io.github.inflationx.calligraphy3;

import ohos.agp.text.Font;

/**
 * There are two ways to set typeface for custom views:
 * <ul>
 *     <li>Or via reflection. If custom view already has setTypeface method you can
 *     register it during Calligraphy configuration
 *     {@link CalligraphyConfig.Builder#addCustomViewWithSetTypeface(Class)}</li>
 * </ul>
 * First way is faster but encourage more effort from the developer to implements interface. Second one
 * requires less effort but works slowly cause reflection calls.
 *
 * @author Dmitriy Tarasov
 */
public interface HasTypeface {

    void setTypeface(Font typeface);
}
