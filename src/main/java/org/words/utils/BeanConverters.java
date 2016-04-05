package org.words.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2016/4/4.
 */
public class BeanConverters {


    public static <S,T> T convert(S frm, Class<T> to) {
        try {
            T dst = to.newInstance();
            BeanUtils.copyProperties(dst, frm);
            return dst;
        }catch (Exception e){
            throw new RuntimeException("conversion error in coping!");
        }
    }

    public static <S,T> List<T> convert(List<S> s, Class<T> t){
        List<T> list = new ArrayList<>();
        for(S val : s){
            list.add(convert(val, t));
        }
        return list;
    }
}
