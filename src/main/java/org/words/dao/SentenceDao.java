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
        List<Word> wordList = currentSession().createQuery("select w from Word w join fetch w.sentences where w.name = 'abandon'").list();
        return new ArrayList<>(wordList.get(0).getSentences());
    }
}
