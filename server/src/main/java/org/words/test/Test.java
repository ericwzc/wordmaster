package org.words.test;

/**
 * Created by Eric on 2016/3/11.
 */
public class Test {
//
//    private boolean lock1 = false;
//    private boolean lock2 = false;
//
//    public synchronized void delete(Session session, String id) {
//        Transaction tx = session.beginTransaction();
//        Sentence s = (Sentence) session.get(Sentence.class, id);
//        System.out.println("Holding sentence in delete with version:" + s.getVersion());
//
//        lock1 = true;
//        notifyAll();
//        while (!lock2) {
//            try {
//                wait();
//            }
//            catch (InterruptedException e) {
//            }
//        }
//        session.delete(s);
//        tx.commit();
//        session.close();
//    }
//
//    public synchronized void update(Session session, Transaction tx) throws InterruptedException {
//        while (!lock1) {
//            wait();
//        }
//        tx.commit();
//        session.close();
//
//        lock2 = true;
//        notifyAll();
//    }
//
//    public void mockDeleteUpdated(final Test test, final SessionFactory sessionFactory, final String id) throws InterruptedException {
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                Session session1 = sessionFactory.openSession();
//                test.delete(session1, id);
//            }
//        });
//        t.start();
//
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        Sentence sentence1 = (Sentence) session.get(Sentence.class, id);
//        sentence1.setEnglish("Second ");
//        System.out.println("Holding sentence id in mockDeleteUpdated:" + sentence1.getVersion());
//        test.update(session, tx);
//    }
//
//    public void mockUpdateDeleted(final Test test, final SessionFactory sessionFactory, final String id) {
//
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                Session session = sessionFactory.openSession();
//                Transaction tx = session.beginTransaction();
//                Sentence sentence1 = (Sentence) session.get(Sentence.class, id);
//                sentence1.setEnglish("Thrid ");
//                System.out.println("Holding sentence id in mockUpdateDeleted:" + sentence1.getVersion());
//                try {
//                    test.update(session, tx);
//                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();
//
//        Session session1 = sessionFactory.openSession();
//        test.delete(session1, id);
//    }
//
//    public void testDefaultFlush(SessionFactory sessionFactory, String id) throws InterruptedException {
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        Sentence sentence = (Sentence) session.get(Sentence.class, id);
//        sentence.setChinese("hh");
//        Query query = session.createQuery("from Sentence s where s.id = :id").setParameter("id", id); // query
//        // will
//        // trigger
//        // auto
//        // flush
//        List<Sentence> list = query.list();
//        tx.commit();
//        session.close();
//    }
//
//    public void testRecordModel() {
//        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        Word word = new Word("test");
//        Sentence sentence = new Sentence("english", "chinese");
//        word.addSentence(sentence);
//        session.persist(word);
//        tx.commit();
//        session.close();
//
//        session = sessionFactory.openSession();
//        tx = session.beginTransaction();
//        sentence = (Sentence) session.createQuery("from Sentence s").uniqueResult();
//        Record record = new Record();
//        record.setSentence(sentence);
//        session.persist(record);
//        tx.commit();
//        session.close();
//    }
//
//    public void testMeaning(){
//        WordMaster wordMaster = new WordMaster();
//        BufferedReader br = null;
//        SessionFactory sf = HibernateUtils.getSessionFactory();
//
//        try {
//            br = new BufferedReader(new FileReader(getClass().getResource("/ielts.txt").getFile()));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                Word word = new Word(line.trim());
//                wordMaster.fetchSentence(true, word);
//                Session session = sf.openSession();
//                Transaction tx = session.beginTransaction();
//
//                Word dbWord = (Word) session.createQuery("select w from Word w left join fetch w.sentences where w.name='" + line.trim() + "'").uniqueResult();
//
//                Map<Sentence, Meaning> sm = new HashMap<>();
//                Map<String, Meaning> txt_m = new HashMap<>();
//
//                for(Sentence sentence : word.getSentences()){
//                    String key = sentence.getMeaning().getTxt();
//                    if(!txt_m.containsKey(key)) {
//                        txt_m.put(key, new Meaning(sentence.getMeaning().getTxt()));
//                    }
//                    Meaning meaning = txt_m.get(key);
//                    sm.put(sentence, meaning);
//                    session.persist(meaning);
//                }
//
//                if(dbWord!= null && dbWord.getSentences() != null) {
//                    for (Sentence sentence : dbWord.getSentences()) {
//                        if (sm.containsKey(sentence)) {
//                            sentence.setMeaning(sm.get(sentence));
//                        }
//                    }
//                }
//
//                tx.commit();
//                session.close();
////                break;
//            }
//        }
//        catch (FileNotFoundException e) {
//        }
//        catch (IOException e) {
//        }finally {
//            if(br != null){
//                try {
//                    br.close();
//                }
//                catch (IOException e) {
//                }
//            }
//        }
//        sf.close();
//    }
//
//    static void play() throws JavaLayerException, IOException {
//        InputStream is = new URL("http://media.merriam-webster.com/audio/prons/en/us/mp3/t/turtle01.mp3").openConnection().getInputStream();
//        BufferedInputStream buffer = new BufferedInputStream(is);
//        Player player = new Player(buffer);
//        player.play();
//    }
//
//    public static void main(String[] args) throws InterruptedException, IOException, JavaLayerException {
//        new WordMaster().downloadMp3s();
//        //		Word word = new Word("conjunction");
//        //		new WordMaster().fetchSentence(true, word);
////        new Test().testMeaning();
////        play();
//        //        new Test().testPlanUser(HibernateUtils.getSessionFactory());
//        //        TransApp.registerConverters();
//        //
//        //		Sentence sentence = new Sentence("english", "sdsd");
//        //        Word word = new Word("e");
//        //        sentence.setWord(word);
//        //		SentenceTO sentenceTO = new SentenceTO();
//        //		try {
//        //			BeanUtils.copyProperties(sentenceTO, sentence);
//        //			System.out.println(sentenceTO);
//        //            Sentence sentence1 = null;
//        //            BeanUtils.copyProperties(sentence, sentenceTO);
//        //            System.out.println(sentence);
//        //        } catch (IllegalAccessException e) {
//        //			e.printStackTrace();
//        //		} catch (InvocationTargetException e) {
//        //			e.printStackTrace();
//        //		}
//
//        //        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
//        //        Transaction tx = session.beginTransaction();
//        //
//        //        List<Word> list = session.createQuery("select distinct s from Word s join fetch s.sentences where s.name = 'abandon'").list();
//        //
//        //        tx.commit();
//        //
//        //        System.out.println("something else");
//        //        for(Sentence sentence : list.get(0).getSentences()){
//        //            System.out.println(sentence);
//        //        }
//
//        //		final Test test = new Test();
//
//        //		final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
//        //		Session session = sessionFactory.openSession();
//        //		Transaction tx = session.beginTransaction();
//        //		Sentence sentence = new Sentence("First Sentence", "第一个句子");
//        //		Word word = new Word("First");
//        //		word.addSentence(sentence);
//        //		String wordID = (String) session.save(word);
//        //		tx.commit();
//        //		session.close();
//        //
//        //		session = sessionFactory.openSession();
//        //		tx = session.beginTransaction();
//        //		Word word2 = (Word) session.get(Word.class, wordID);
//        //		Iterator<Sentence> iter = word2.getSentences().iterator();
//        //		String id = iter.next().getId();
//        //		tx.commit();
//        //		session.close();
//
//        // test.testPlanUser(sessionFactory);
//        // test.testDefaultFlush(sessionFactory, id);
//        // test.mockDeleteUpdated(test, sessionFactory, id);
//        // TODO implement mockUpdateDeleted
//        //		test.mockUpdateDeleted(test, sessionFactory, id);
//    }
//
//    private void testPlanUser(SessionFactory sessionFactory) {
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        User user = new User("Sunny");
//        session.persist(user);
//
//        User user1 = (User) session.createQuery("from User u where u.name = 'Sunny'").uniqueResult();
//        System.out.println("+" + user1.getName() + "+");
//
//        throw new RuntimeException("unknown error!");
//    }
}
