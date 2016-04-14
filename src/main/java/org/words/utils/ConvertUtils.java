package org.words.utils;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Copy by name
 @author Eric Wang
 **/
public class ConvertUtils {

        static <T> T convert(Object src, Class<T> tClass, Map map) throws IllegalAccessException, InstantiationException, ClassNotFoundException {//TODO support Collection
            T result = tClass.newInstance();
            map.put(src, result);
            Class<?> srcClass = src.getClass();
            for (Field field : srcClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getType().getPackage().getName().equals("org.words.hbm") && !field.getType().getName().endsWith("TO")) {
                    try {
                        Field dstField = tClass.getDeclaredField(field.getName());
                        dstField.setAccessible(true);
                        dstField.set(result, field.get(src));
                    }
                    catch (NoSuchFieldException e) {
                        System.err.println(e);
                    }
                }
                else {
                    Object srcVal = field.get(src);
                    try {
                        Field dstField = tClass.getDeclaredField(field.getName());
                        dstField.setAccessible(true);
                        if (map.containsKey(srcVal)) {
                            dstField.set(result, map.get(srcVal));
                        }
                        else {
                            Object to = convert(srcVal, resolveTargetClassName(field), map);
                            dstField.setAccessible(true);
                            dstField.set(result, to);
                        }
                    }
                    catch (NoSuchFieldException e) {
                        System.err.println(e);
                    }
                }
            }
            return result;
        }

        static Class resolveTargetClassName(Field field) throws ClassNotFoundException {
            if(field.getType().getPackage().equals("org.words,hbm")){
                return Class.forName(field.getType().getName().replace(".hbm", ".to") + "TO");
            }
            if(field.getType().getPackage().equals("org.words.to")){
                return Class.forName(field.getType().getName().replace("TO", ""));
            }
            throw new ClassNotFoundException();
        }
}

