package org.words.test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.words.hbm.Sentence;
import org.words.hbm.Word;
import org.words.utils.HibernateUtils;

import java.util.List;

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

    public synchronized void update(Session session, Transaction tx) throws InterruptedException {
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
        test.update(session, tx);
    }

    public void testDefaultFlush(SessionFactory sessionFactory, String id) throws InterruptedException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Sentence sentence = (Sentence) session.get(Sentence.class, id);
        sentence.setChinese("hh");
        Query query = session.createQuery("from Sentence s where s.id = :id").setParameter("id", id); // query will trigger auto flush
        List<Sentence> list = query.list();
        Thread.currentThread().sleep(8000);
        tx.commit();
        session.close();
    }

    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();

        final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Sentence sentence = new Sentence("First Sentence", "第一个句子");
        Word word = new Word("First");
        word.addSentence(sentence);
        String wordID = (String) session.save(word);
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.load(word, wordID);
        tx.commit();
        session.close();

//        test.testDefaultFlush(sessionFactory, id);
//        test.mockDeleteUpdated(test, sessionFactory, id);
        //TODO implement mockUpdateDeleted
    }
}
