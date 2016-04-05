package org.words.converter;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * This works as long as name matches
 *
 * @author Eric Wang
 **/
public class GenericConverter<F, T> extends AbstractConverter {

    private final Class<F> fClass;
    private final Class<T> tClass;

    public GenericConverter(Class<F> fClass, Class<T> tClass) {
        this.fClass = fClass;
        this.tClass = tClass;
    }

    @Override
    protected Object convertToType(Class type, Object value) throws Throwable {
        if (value == null) {
            return null;
        }
        if (!(fClass.isInstance(value))) {
            throw new ConversionException(value + "cannot convert to " + tClass);
        }

        T obj = tClass.newInstance();
        BeanUtils.copyProperties(obj, value);
        return obj;
    }
    @Override
    protected Class getDefaultType() {
        return null;
    }
}

