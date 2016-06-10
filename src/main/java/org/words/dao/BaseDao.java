package org.words.dao;

import org.hibernate.Session;
import org.words.utils.HibernateUtils;

import java.io.Serializable;

/**
 * Base dao with default implementation
 *
 * @param <T> Entity type
 */
public class BaseDao<T> {
    protected Session currentSession(){
        return HibernateUtils.getSessionFactory().getCurrentSession();
    }

    /**
     * Save entity
     * @param entity entity
     */
    public void save(T entity){
        currentSession().save(entity);
    }

    /**
     * Get entity
     * @param clazz entity class
     * @param prmKey primary key
     * @return entity obj
     */
    public T get(Class<T> clazz, Serializable prmKey){
        //noinspection unchecked
        return (T) currentSession().get(clazz, prmKey);
    }

    /**
     * Delete entity
     * @param entity entity
     */
    @SuppressWarnings("unused")
    public void delete(T entity){
        currentSession().delete(entity);
    }
}
