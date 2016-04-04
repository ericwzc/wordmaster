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
    public List<Sentence> getSentences(){
        List<Sentence> entityList = currentSession().createQuery("select s from Sentence s join fetch s.word b where b.name = 'abandon'").list();
        return entityList;
    }
}
