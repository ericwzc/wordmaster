package org.words.dao;

import org.hibernate.Session;
import org.words.utils.HibernateUtils;

import java.io.Serializable;

/**
 * Created by Eric on 2016/3/19.
 */
public class BaseDao<T> {
    protected Session currentSession(){
        return HibernateUtils.getSessionFactory().getCurrentSession();
    }

    public void save(T entity){
        currentSession().save(entity);
    }

    public T get(Class<T> clazz, Serializable prmKey){
        return (T) currentSession().get(clazz, prmKey);
    }

    public void delete(T entity){
        currentSession().delete(entity);
    }
}
