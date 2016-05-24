package org.words.dao;

import org.words.hbm.Sentence;

import java.util.List;

/**
 * Created by Eric on 2016/3/19.
 */
public class SentenceDao extends BaseDao<Sentence> {
    public List<Sentence> getSentences(){
        List<Sentence> wordList = currentSession().createQuery("select s from Sentence s join fetch s.word where s.word.name = 'abandon'").list();
        return wordList;
    }

    public List<Sentence> getNewSentences(int num){
        List<Sentence> result = currentSession().createQuery("select s from Sentence s join fetch s.word where s.record is EMPTY").setFirstResult(0).setMaxResults(num).list();
        return result;
    }
}

