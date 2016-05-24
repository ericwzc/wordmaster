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
        return currentSession().createQuery("FROM Record r where r.status=" + Status.STUDIED.ordinal()).setFirstResult(0).setMaxResults(num).list();
    }
}

