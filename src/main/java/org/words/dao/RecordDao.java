package org.words.dao;

import org.words.common.Status;
import org.words.hbm.Record;

import java.util.List;

/**
 * RecordDao
 *
 * @author Eric Wang
 **/
public class RecordDao extends BaseDao<Record> {

    /**
     * Persist records one by one
     * @param records record entities
     */
    public void saveRecords(List<Record> records) {
        for (Record record : records) {
            currentSession().persist(record);
        }
    }

    /**
     * Load records for review
     *
     * @param num numer limit
     * @return list of Record Entities
     */
    public List<Record> load2ReviewRecords(int num) {
        //noinspection unchecked
        return currentSession().createQuery("select r FROM Record r join fetch r.sentence s join fetch s.word join fetch s.meaning where r.status=" + Status.STUDIED.ordinal() + " order by r.counter").setFirstResult(0).setMaxResults(num).list();
    }

    /**
     * Load newly added records
     *
     * @return list of Record Entities
     */
    public List<Record> loadNewRecords(){
        //noinspection unchecked
        return currentSession().createQuery("select r FROM Record r join fetch r.sentence s join fetch s.word join fetch s.meaning where r.status=" + Status.NEW.ordinal()).list();
    }
}

