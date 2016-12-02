package org.words.config;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * TODO The class DbModule is supposed to be documented...
 *
 * @author Eric Wang
 */
public class DbModule extends AbstractModule{
    // <editor-fold desc="static class">
    // </editor-fold>
    // <editor-fold desc="static constants">
    // </editor-fold>
    // <editor-fold desc="static method">
    // </editor-fold>
    // <editor-fold desc="instance data">
    // </editor-fold>
    // <editor-fold desc="constructor">
    // </editor-fold>
    // <editor-fold desc="public">
    // </editor-fold>
    // <editor-fold desc="private">
    // </editor-fold>
    // <editor-fold desc="protected">
    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(new Configuration().configure().buildSessionFactory());
    }
    // </editor-fold>
}

