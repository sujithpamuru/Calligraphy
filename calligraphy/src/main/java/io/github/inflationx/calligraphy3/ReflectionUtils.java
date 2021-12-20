package io.github.inflationx.calligraphy3;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectionUtils {

    /**
     * TYPE.
     */
    private static final int HILOG_TYPE = 3;
    /**
     * DOMAIN.
     */
    private static final int HILOG_DOMAIN = 0xD000F00;
    /**
     * LABEL.
     */
    private static final HiLogLabel LABEL = new HiLogLabel(HILOG_TYPE, HILOG_DOMAIN, "Calligraphy");

    static Method getMethod(Class clazz, String methodName) {
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    static void invokeMethod(Object object, Method method, Object... args) {
        try {
            if (method == null) {
                return;
            }
            method.invoke(object, args);
        } catch (IllegalAccessException e) {
            HiLog.error(LABEL, e.getMessage());
        } catch (InvocationTargetException e) {
            HiLog.error(LABEL, e.getMessage());
        }
    }
}