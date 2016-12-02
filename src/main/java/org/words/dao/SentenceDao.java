package org.words.dao;

import com.google.inject.Singleton;
import org.words.hbm.Sentence;

import java.util.List;

/**
 * Sentence dao class
 */
@SuppressWarnings("all")
@Singleton
public class SentenceDao extends BaseDao<Sentence> {
    /**
     * Get unstudied sentences of the specified limit
     * @param num limit number
     *
     * @return list of Sentence Entities
     */
    public List<Sentence> getNewSentences(int num){
        List<String> selected = currentSession().createQuery("select s.id from Sentence s where s.record is EMPTY order by s.english").setFirstResult(0).setMaxResults(num).list();
        return currentSession().createQuery("select s from Sentence s join fetch s.word join fetch s.meaning where s.id in (:selected)").setParameterList("selected", selected).list();
    }
}

