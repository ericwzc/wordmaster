package org.words.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Utils to hold sessionFactory single instance
 */
public class HibernateUtils {
    private HibernateUtils(){}

    private static class SingletonWrapper {
        @SuppressWarnings("deprecation")
        private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        private SingletonWrapper() {}
    }

    public static SessionFactory getSessionFactory(){
        return SingletonWrapper.sessionFactory;
    }

}
