package org.words.dao;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.common.Status;
import org.words.hbm.Record;

import java.util.List;

/**
 * RecordDao
 *
 * @author Eric Wang
 **/
public class RecordDao extends BaseDao<Record> {

    public void saveRecords(List<Record> records) {
        for (Record record : records) {
            currentSession().persist(record);
        }
    }

    public List<Record> load2ReviewRecords(int num) {
        return currentSession().createQuery("select r FROM Record r join fetch r.sentence s join fetch s.word join fetch s.meaning where r.status=" + Status.STUDIED.ordinal() + " order by r.counter").setFirstResult(0).setMaxResults(num).list();
    }

    public List<Record> loadNewRecords(){
        return currentSession().createQuery("select r FROM Record r join fetch r.sentence s join fetch s.word join fetch s.meaning where r.status=" + Status.NEW.ordinal()).list();
    }
}

