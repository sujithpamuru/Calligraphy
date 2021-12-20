package io.github.inflationx.calligraphy3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectionUtils {

    private static final String TAG = "Info Message";

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
            LogUtil.error(TAG, e.getMessage());
        } catch (InvocationTargetException e) {
            LogUtil.error(TAG, e.getMessage());
        }
    }
}
