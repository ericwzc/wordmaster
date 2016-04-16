package org.words.dao;

import org.words.hbm.Sentence;
import org.words.hbm.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2016/3/19.
 */
public class SentenceDao extends BaseDao<Sentence> {
    public List<Sentence> getSentences(){
        List<Sentence> wordList = currentSession().createQuery("select s from Sentence s join fetch s.word where s.word.name = 'abandon'").list();
        return wordList;
    }
}

