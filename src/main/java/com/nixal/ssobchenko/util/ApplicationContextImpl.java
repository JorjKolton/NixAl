package com.nixal.ssobchenko.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextImpl.class);

    private final Map<Class<?>, Object> cache = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> tClass) {
        if (cache.containsKey(tClass)) {
            return (T) cache.get(tClass);
        }

        try {

            List<Object> params = new ArrayList<>();
            for (Constructor<?> constr : tClass.getDeclaredConstructors()) {
                constr.setAccessible(true);
                if (constr.isAnnotationPresent(Autowired.class)) {
                    for (Parameter parameter : constr.getParameters()) {
                        params.add(this.getObject(parameter.getType()));
                    }

                    Object obj = constr.newInstance(params.toArray());
                    if (tClass.isAnnotationPresent(Singleton.class)) {
                        cache.put(tClass, obj);
                    }
                    return (T) setInstanceField(obj);
                }
            }

            return tClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Object setInstanceField(Object obj) {
        Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.getName().equals("instance"))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        field.set(obj, obj);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        LOGGER.info(e.fillInStackTrace().toString());
                    }
                });
        return obj;
    }

}