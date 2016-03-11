package org.words.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.words.hbm.Sentence;
import org.words.utils.HibernateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2016/3/11.
 */
public class Test {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new Sentence( "First sentence!", "第一个句子" ) );
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Sentence> result = session.createQuery( "from Sentence" ).list();
        for (  Sentence sentence :  result ) {
            System.out.println( sentence );
        }
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
