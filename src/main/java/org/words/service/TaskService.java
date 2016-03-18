package org.words.service;

import org.words.hbm.Task;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2016/3/17.
 */
public interface TaskService {
    List<Task> getTasks(Date date);
    void createTasks(Collection<Task> tasks);
}
