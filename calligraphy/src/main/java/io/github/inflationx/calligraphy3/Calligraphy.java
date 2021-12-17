package io.github.inflationx.calligraphy3;

import ohos.app.Context;

import ohos.agp.components.AttrSet;
import ohos.agp.text.Font;
import ohos.agp.components.Component.LayoutRefreshedListener;
import ohos.global.config.ConfigManager;
import ohos.global.configuration.Configuration;
import ohos.global.configuration.DeviceCapability;
import ohos.global.resource.*;
import ohos.global.resource.solidxml.Pattern;
import ohos.global.resource.solidxml.SolidXml;
import ohos.global.resource.solidxml.Theme;
import ohos.global.resource.solidxml.TypedAttribute;
import ohos.agp.components.Text;
import ohos.agp.utils.TextTool;
import ohos.agp.components.Component;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

class Calligraphy {

    private static final String ACTION_BAR_TITLE = "action_bar_title";

    private static final String ACTION_BAR_SUBTITLE = "action_bar_subtitle";

    /**
     * An even dirtier way to see if the TextView is part of the ActionBar
     *
     * @param view TextView to check is Title
     * @return true if it is.
     */

    protected static boolean isActionBarTitle(Text view) {
        if (matchesResourceIdName(view, ACTION_BAR_TITLE)) {
            return true;
        }
        return false;
    }

    /**
     * An even dirtier way to see if the TextView is part of the ActionBar
     *
     * @param view TextView to check is Title
     * @return true if it is.
     */

    protected static boolean isActionBarSubTitle(Text view) {
        if (matchesResourceIdName(view, ACTION_BAR_SUBTITLE)) {
            return true;
        }
        return false;
    }

    /**
     * Use to match a view against a potential view id. Such as ActionBar title etc.
     *
     * @param view    not null view you want to see has resource matching name.
     * @param matches not null resource name to match against. Its not case sensitive.
     * @return true if matches false otherwise.
     */

    protected static boolean matchesResourceIdName(Component view, String matches) {
        if (view.getId() == Component.ID_DEFAULT) {
            return false;
        }
        final String resourceEntryName = view.getResourceManager().toString();
        return resourceEntryName.equalsIgnoreCase(matches);
    }

    private final CalligraphyConfig mCalligraphyConfig;

    private final int[] mAttributeId;

    public Calligraphy(CalligraphyConfig calligraphyConfig) {
        mCalligraphyConfig = calligraphyConfig;
        this.mAttributeId = new int[]{calligraphyConfig.getAttrId()};
    }

    /**
     * Handle the created view
     *
     * @param view    nullable.
     * @param context shouldn't be null.
     * @param attrs   shouldn't be null.
     * @return null if null is passed in.
     */

    public Component onViewCreated(Component view, Context context, AttrSet attrs) {
        if (view != null && view.getTag() != Boolean.TRUE) {
            onViewCreatedInternal(view, new ResourceManager() {
                @Override
                public Resource getResource(int i) throws IOException, NotExistException {
                    return null;
                }

                @Override
                public String getIdentifier(int i) throws IOException, NotExistException {
                    return null;
                }

                @Override
                public Element getElement(int i) throws IOException, NotExistException, WrongTypeException {
                    return null;
                }

                @Override
                public Theme getTheme(int i) throws IOException, NotExistException, WrongTypeException {
                    return null;
                }

                @Override
                public SolidXml getSolidXml(int i) throws IOException, NotExistException, WrongTypeException {
                    return null;
                }

                @Override
                public RawFileEntry getRawFileEntry(String s) {
                    return null;
                }

                @Override
                public ConfigManager getConfigManager() {
                    return null;
                }

                @Override
                public String getMediaPath(int i) throws IOException, NotExistException, WrongTypeException {
                    return null;
                }

                @Override
                public Configuration getConfiguration() {
                    return null;
                }

                @Override
                public DeviceCapability getDeviceCapability() {
                    return null;
                }

                @Override
                public void updateConfiguration(Configuration configuration, DeviceCapability deviceCapability) {

                }

                @Override
                public Pattern createPattern(List<TypedAttribute.AttrData> list) {
                    return null;
                }

                @Override
                public Theme createTheme(List<TypedAttribute.AttrData> list) {
                    return null;
                }
            }, context, attrs);
            view.setTag(tagMap);
        }
        return view;
    }

    void onViewCreatedInternal(Component view, final ResourceManager resourceManager, Context context, AttrSet attrs) {
        if (view instanceof Text) {
            // Fast path the setting of TextView's font, means if we do some delayed setting of font,
            // which has already been set by use we skip this TextView (mainly for inflating custom,
            // TextView's inside the Toolbar/ActionBar).
            if (TypefaceUtils.isLoaded(((Text) view).getFont())) {
                return;
            }
            // Try to get typeface attribute value
            // Since we're not using namespace it's a little bit tricky
            // Check xml attrs, style attrs and text appearance for font path
            String textViewFont = resolveFontPath(context, attrs);
            textViewFont = applyFontMapper(textViewFont);
            // Still need to defer the Native action bar, appcompat-v7:21+ uses the Toolbar underneath. But won't match these anyway.
            final boolean deferred = matchesResourceIdName(view, ACTION_BAR_TITLE) || matchesResourceIdName(view, ACTION_BAR_SUBTITLE);
            CalligraphyUtils.applyFontToTextView(context, (Text) view, mCalligraphyConfig, textViewFont, deferred);
        }
        // AppCompat API21+ The ActionBar doesn't inflate default Title/SubTitle, we need to scan the
        // Toolbar(Which underlies the ActionBar) for its children.
        // Try to set typeface for custom views using interface method or via reflection if available
        if (view instanceof HasTypeface) {
            String textViewFont = resolveFontPath(context, attrs);
            textViewFont = applyFontMapper(textViewFont);
            Font typeface = getDefaultTypeface(resourceManager, textViewFont);
            if (typeface != null) {
                ((HasTypeface) view).setTypeface(typeface);
            }
        } else if (mCalligraphyConfig.isCustomViewTypefaceSupport() && mCalligraphyConfig.isCustomViewHasTypeface(view)) {
            final Method setTypeface = ReflectionUtils.getMethod(view.getClass(), "setTypeface");
            String fontPath = resolveFontPath(context, attrs);
            fontPath = applyFontMapper(fontPath);
            Font typeface = getDefaultTypeface(resourceManager, fontPath);
            if (setTypeface != null && typeface != null) {
                ReflectionUtils.invokeMethod(view, setTypeface, typeface);
            }
        }
    }

    private Font getDefaultTypeface(ResourceManager context, String fontPath) {
        if (TextTool.isNullOrEmpty(fontPath)) {
            fontPath = mCalligraphyConfig.getFontPath();
        }
        if (!TextTool.isNullOrEmpty(fontPath)) {
            return TypefaceUtils.load(context, fontPath);
        }
        return null;
    }

    /**
     * Resolving font path from xml attrs, style attrs or text appearance
     */

    private String resolveFontPath(Context context, AttrSet attrs) {
        // Try view xml attributes
        String textViewFont = CalligraphyUtils.pullFontPathFromView(context, attrs, mAttributeId);
        // Try view style attributes
        if (TextTool.isNullOrEmpty(textViewFont)) {
            textViewFont = CalligraphyUtils.pullFontPathFromStyle(context, attrs, mAttributeId);
        }
        // Try View TextAppearance
        if (TextTool.isNullOrEmpty(textViewFont)) {
            textViewFont = CalligraphyUtils.pullFontPathFromTextAppearance(context, attrs, mAttributeId);
        }
        return textViewFont;
    }

    private String applyFontMapper(String textViewFont) {
        FontMapper fontMapper = mCalligraphyConfig.getFontMapper();
        return fontMapper != null ? fontMapper.map(textViewFont) : textViewFont;
    }

    private static class ToolbarLayoutListener implements LayoutRefreshedListener {

        static String BLANK = " ";

        private final WeakReference<Calligraphy> mCalligraphyFactory;

        private final WeakReference<Context> mContextRef;

        private ToolbarLayoutListener(final Calligraphy calligraphy, final Context context) {
            mCalligraphyFactory = new WeakReference<>(calligraphy);
            mContextRef = new WeakReference<>(context);
        }

        @Override
        public void onRefreshed(Component component) {
            // param mapping start
            int width = component.getWidth();
            int height = component.getHeight();
            final Context context = mContextRef.get();
            final Calligraphy factory = mCalligraphyFactory.get();

        }

    }

    HashMap<Integer, Object> tagMap = new HashMap<>();
}