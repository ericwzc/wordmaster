package org.words.utils;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.hbm.Sentence;
import org.words.hbm.Word;
import org.words.to.WordTO;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Copy by name
 *
 * @author Eric Wang
 **/
public class ConvertUtils {

    public static <T> T convert(Object src, Class<T> tClass){
        try {
            return convert(src, tClass, new HashMap());
        } catch (Exception e) {
            throw new RuntimeException("error in conversion");
        }
    }

    static <T> T convert(Object src, Class<T> tClass, Map map) throws IllegalAccessException, InstantiationException, ClassNotFoundException {//TODO support Collection
        if(src == null){
            return null;
        }

        T result = tClass.newInstance();
        map.put(src, result);
        Class<?> srcClass = src.getClass();
        for (Field field : srcClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Field dstField = tClass.getDeclaredField(field.getName());
                dstField.setAccessible(true);
                if(field.get(src) == null){
                    dstField.set(result, null);
                }else if (Set.class.isAssignableFrom(field.getType())) {
                    Set set = (Set) field.get(src);

                    if (set == null || set.isEmpty())
                        dstField.set(result, set);
                    else {
                        Set newSet = (Set) field.get(src).getClass().newInstance();

                        for (Object obj : set) {
                            Class<?> targetType = resolveTargetClassName(obj.getClass());
                            newSet.add(convert(obj, targetType, map));
                        }

                        dstField.set(result, newSet);
                    }
                } else if (!field.get(src).getClass().getPackage().getName().equals("org.words.hbm") && !field.get(src).getClass().getName().endsWith("TO")) {
                    dstField.set(result, field.get(src));
                } else {
                    Object srcVal = field.get(src);
                    if (map.containsKey(srcVal)) {
                        dstField.set(result, map.get(srcVal));
                    } else {
                        Object to = convert(srcVal, resolveTargetClassName(field), map);
                        dstField.setAccessible(true);
                        dstField.set(result, to);
                    }
                }
            } catch (NoSuchFieldException e) {
            }
        }
        return result;
    }

    static Class resolveTargetClassName(Field field) throws ClassNotFoundException {
        if (field.getType().getPackage().getName().equals("org.words,hbm")) {
            return Class.forName(field.getType().getName().replace(".hbm", ".to") + "TO");
        }
        if (field.getType().getPackage().getName().equals("org.words.to")) {
            return Class.forName(field.getType().getName().replace("TO", ""));
        }
        throw new ClassNotFoundException();
    }

    static Class resolveTargetClassName(Class<?> clazz) throws ClassNotFoundException {
        if (clazz.getPackage().getName().equals("org.words.hbm")) {
            return Class.forName(clazz.getName().replace(".hbm", ".to") + "TO");
        }
        if (clazz.getPackage().getName().equals("org.words.to")) {
            return Class.forName(clazz.getName().replace("TO", ""));
        }

        return clazz;
    }

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Word word = new Word("aa");
        word.setId("sdfsd");
        word.setVersion(0);

        Sentence sentence = new Sentence("sddsds", "chinese");
        sentence.setVersion(0);
        sentence.setTask(null);
        sentence.setId("abcd");

        word.addSentence(sentence);

        WordTO to =  convert(word, WordTO.class, new HashMap());
        System.out.println(to.getName());

    }

}

