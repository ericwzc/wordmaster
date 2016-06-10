package org.words.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Convert Utils for transforming between entities and transfer object
 *
 * @author Eric Wang
 **/
public class ConvertUtils {

    public static final String ORG_WORDS_HBM = "org.words.hbm";
    public static final String ORG_WORDS_TO = "org.words.to";
    private static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    private ConvertUtils() {
    }

    /**
     * Convert recursively, start from root of bean, use a map to keep track visited object, assuming same name in
     * source object and target object
     *
     * @param src    from bean
     * @param tClass target bean
     * @param <T>    target bean class
     * @return target bean
     */
    public static <T> T convert(Object src, Class<T> tClass) {
        try {
            return convert(src, tClass, new HashMap());
        } catch (Exception e) {
            logger.error("error in conversion:{}", e);
            throw new RuntimeException("error in conversion"); //NOSONAR
        }
    }

    static <T> T convert(Object src, Class<T> tClass, Map map) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        if (src == null) {
            return null;
        }

        T result = tClass.newInstance();
        //noinspection unchecked
        map.put(src, result);
        Class<?> srcClass = src.getClass();
        for (Field field : srcClass.getDeclaredFields()) {
            field.setAccessible(true);
            Field dstField = tClass.getDeclaredField(field.getName());
            dstField.setAccessible(true);
            if (field.get(src) == null) {
                dstField.set(result, null);
            } else if (Set.class.isAssignableFrom(field.getType())) {
                setSetField(field, dstField, src, result, map);
            } else if (!field.get(src).getClass().getPackage().getName().equals(ORG_WORDS_HBM) && !field.get(src).getClass().getName().endsWith("TO")) {
                dstField.set(result, field.get(src));
            } else {
                copy(src, result, field, dstField, map);
            }
        }
        return result;
    }

    private static void copy(Object src, Object result, Field field, Field dstField, Map map) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchFieldException {
        Object srcVal = field.get(src);
        if (map.containsKey(srcVal)) {
            dstField.set(result, map.get(srcVal));
        } else {
            Object to = convert(srcVal, resolveTargetClassName(field), map);
            dstField.setAccessible(true);
            dstField.set(result, to);
        }
    }

    private static void setSetField(Field field, Field dstField, Object src, Object result, Map map) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchFieldException {
        Set set = (Set) field.get(src);

        if (set == null || set.isEmpty()) {
            dstField.set(result, set);
        } else {
            Set newSet = new HashSet<>();

            for (Object obj : set) {
                Class<?> targetType = resolveTargetClassName(obj.getClass());
                //noinspection unchecked
                newSet.add(convert(obj, targetType, map));
            }

            dstField.set(result, newSet);
        }
    }

    private static Class resolveTargetClassName(Field field) throws ClassNotFoundException {
        if (field.getType().getPackage().getName().equals(ORG_WORDS_HBM)) {
            return Class.forName(field.getType().getName().replace(".hbm", ".to") + "TO");
        }
        if (field.getType().getPackage().getName().equals(ORG_WORDS_TO)) {
            return Class.forName(field.getType().getName().replace(".to", ".hbm").replace("TO", ""));
        }
        throw new ClassNotFoundException();
    }

    private static Class resolveTargetClassName(Class<?> clazz) throws ClassNotFoundException {
        if (clazz.getPackage().getName().equals(ORG_WORDS_HBM)) {
            return Class.forName(clazz.getName().replace(".hbm", ".to") + "TO");
        }
        if (clazz.getPackage().getName().equals(ORG_WORDS_TO)) {
            return Class.forName(clazz.getName().replace(".to", ".hbm").replace("TO", ""));
        }

        return clazz;
    }
}

