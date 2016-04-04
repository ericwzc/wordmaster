package org.words.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Created by Eric on 2016/4/4.
 */
public class BeanConverters {

    private static Mapper mapper = new DozerBeanMapper();

    public static <S,T> T convert(S frm, Class<T> to) {
        try {
            return mapper.map(frm, to);
        }catch (Exception e){
            throw new RuntimeException("conversion error in coping!");
        }
    }

    public static <S,T> T convert(S frm, T to){
        mapper.map(frm, to);
        return to;
    }
}
