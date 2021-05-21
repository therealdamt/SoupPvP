package xyz.damt.util;

import com.google.common.annotations.Beta;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Beta
public class RegisterUtil {

    public void loadAllAbstracts(Class<?> abstact, String packagename) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses(packagename)) {
            final Class<?> clazz = info.load();
            if (clazz.isAssignableFrom(abstact)) {
                Constructor<?> constructor = clazz.getConstructor();
                constructor.newInstance();
            }
        }

    }
}



