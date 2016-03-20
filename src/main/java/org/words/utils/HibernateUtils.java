package org.words.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Created by Eric on 2016/3/11.
 */
// TODO replace with spring later
public class HibernateUtils {
    private HibernateUtils(){}

    private static class SingletonWrapper {
        private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static void startTransaction(){
        getSessionFactory().getCurrentSession().beginTransaction();
    }

    public static void commit(){
        getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    public static void rollback(){
        getSessionFactory().getCurrentSession().getTransaction().rollback();
    }

    public static SessionFactory getSessionFactory(){
        return SingletonWrapper.sessionFactory;
    }

}
