package org.words.dao;

import org.words.hbm.Task;

import java.util.Date;

/**
 * Created by Eric on 2016/3/17.
 */
public class TaskDao extends BaseDao<Task> {

    public Task getTaskForDate(Date date) {
        Date start = new Date(date.getYear(), date.getMonth(), date.getDate());
        Date end = new Date(start.getTime() + 60 * 60 * 24 * 1000);
        Task task = (Task) currentSession().createQuery("select t from Task t join fetch t.sentences s join fetch s.word where t.deadLine between :yesterday and :date")
                .setDate("yesterday", start)
                .setDate("date", end).list().get(0);

        return task;
    }
}
