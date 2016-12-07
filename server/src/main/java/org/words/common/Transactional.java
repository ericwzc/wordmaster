package org.words.common;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker interface to be transactional, remove boilerplate code
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Transactional {
}
