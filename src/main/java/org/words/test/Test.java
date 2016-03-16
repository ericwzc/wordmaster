package org.words.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.words.hbm.Sentence;
import org.words.utils.HibernateUtils;

/**
 * Created by Eric on 2016/3/11.
 */
public class Test {

    private boolean lock1 = false;
    private boolean lock2 = false;

    public synchronized void delete(Session session, String id){
        Transaction tx = session.beginTransaction();
        Sentence s = (Sentence) session.get(Sentence.class, id);
        System.out.println("Holding sentence in delete with version:" + s.getVersion());

        lock1 = true;
        notifyAll();
        while(!lock2) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        session.delete(s);
        tx.commit();
        session.close();
    }

    public synchronized void sentenceHold(Session session, Transaction tx) throws InterruptedException {
        while (!lock1){
            wait();
        }
        tx.commit();
        session.close();

        lock2 = true;
        notifyAll();
    }

    public void mockDeleteUpdated(final Test test, final SessionFactory sessionFactory, final  String id) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Session session1 = sessionFactory.openSession();
                test.delete(session1, id);
            }
        });
        t.start();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Sentence sentence1 = (Sentence) session.get(Sentence.class, id);
        sentence1.setEnglish("Second ");
        System.out.println("Holding sentence id in main:" + sentence1.getVersion());
        test.sentenceHold(session, tx);
    }

    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();

        final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        final String id = (String) session.save(new Sentence("First sentence!", "第一个句子"));
        tx.commit();
        session.close();

        test.mockDeleteUpdated(test, sessionFactory, id);
        //TODO implement mockUpdateDeleted
    }
}
