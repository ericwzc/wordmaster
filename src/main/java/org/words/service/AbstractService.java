package org.words.service;

import org.hibernate.Session;
import org.words.utils.HibernateUtils;

/**
 * Created by Eric on 2016/3/17.
 */
public abstract class AbstractService {

    public void execute(){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            doExecute(session);
            HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
        }catch (RuntimeException e){
            HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
    }

    protected abstract void doExecute(Session session);
}
