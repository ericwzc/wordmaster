package org.words.dao;

import org.words.hbm.Sentence;
import org.words.to.SentenceTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2016/3/19.
 */
public class SentenceDao extends BaseDao<Sentence> {
    public List<SentenceTO> getSentences(){
        List<SentenceTO> list = new ArrayList<>();
        list.add(new SentenceTO("sdds","dssddsds"));
//        Sentence sentence = (Sentence) currentSession().createQuery("from Sentence s").setReadOnly(true).setMaxResults(1).uniqueResult();
//        SentenceTO sentenceTO = new SentenceTO();
//        try {
//            BeanUtils.copyProperties(sentenceTO, sentence);
//        } catch (IllegalAccessException e) {
//        } catch (InvocationTargetException e) {
//        }
        return list;//TODO
    }
}
