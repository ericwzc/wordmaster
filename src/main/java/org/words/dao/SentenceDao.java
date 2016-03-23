package org.words.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.words.hbm.Sentence;
import org.words.to.SentenceTO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2016/3/19.
 */
public class SentenceDao extends BaseDao<Sentence> {
    public List<SentenceTO> getSentences(){
        List<Sentence> entityList = currentSession().createQuery("select s from Sentence s join fetch s.word b where b.name = 'abandon'").list();
        List<SentenceTO> list = new ArrayList<>(entityList.size());

        try {
            for(Sentence sentence : entityList){
                SentenceTO sentenceTO = new SentenceTO();
                list.add(sentenceTO);
                BeanUtils.copyProperties(sentenceTO, sentence);
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return list;
    }
}
