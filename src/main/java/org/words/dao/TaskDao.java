package org.words.dao;

import com.google.inject.Singleton;
import org.words.hbm.Task;

import java.util.Date;

/**
 * Task dao class
 */
@Singleton
public class TaskDao extends BaseDao<Task> {

    /**
     * Get tasks for particular date
     * @param date specified date
     *
     * @return Task Entity
     */
    public Task getTaskForDate(Date date) {
        //noinspection all
        Date start = new Date(date.getYear(), date.getMonth(), date.getDate());
        Date end = new Date(start.getTime() + 60 * 60 * 24 * 1000);
        //noinspection JpaQlInspection
        return  (Task) currentSession().createQuery("select t from Task t join fetch t.sentences s join fetch s.word where t.deadLine between :yesterday and :date")
                .setDate("yesterday", start)
                .setDate("date", end).list().get(0);
    }
}
