package org.words.converter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eric on 2016/4/5.
 */
public class SetConverter extends AbstractConverter {

    @Override
    public Object convert(Class type, Object value) {
        try {
            return convertToType(type, value);
        } catch (Throwable throwable) {
            return handleError(type, value, throwable);
        }
    }

    @Override
    protected Object convertToType(Class type, Object value) throws Throwable {
        if (value == null)
            return null;

        if (!(value instanceof Set))
            throw new RuntimeException("illegal type " + value);

        Set srcSet = (Set) value;
        Set set = new HashSet<>();
        for (Object val : srcSet) {
            Object obj = null;
            if (val.getClass().getName().endsWith("TO")) {
                obj = Class.forName(val.getClass().getName().replaceFirst("TO", "").replaceFirst("to\\.", "hbm.")).newInstance();
            } else {
                obj = Class.forName(val.getClass().getName().replaceFirst("hbm\\.", "to.") + "TO").newInstance();
            }
            BeanUtils.copyProperties(obj, val);
            set.add(obj);
        }
        return set;
    }

    @Override
    protected Class getDefaultType() {
        return null;
    }
}
