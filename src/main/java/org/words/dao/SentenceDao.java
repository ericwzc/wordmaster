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
        List<String> selected = currentSession().createQuery("select s.id from Sentence s where s.record is EMPTY order by s.english").setFirstResult(0).setMaxResults(num).list();
        List<Sentence> result = currentSession().createQuery("select s from Sentence s join fetch s.word join fetch s.meaning where s.id in (:selected)").setParameterList("selected", selected).list();
        return result;
    }
}

