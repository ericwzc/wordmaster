package org.words.service;

import org.words.common.Transactional;
import org.words.hbm.Task;
import org.words.to.SentenceTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2016/3/17.
 */
public interface TaskService {
    List<Task> getTasks(Date date);
    void createTasks(Collection<Task> tasks);
    @Transactional
    List<SentenceTO> getSentences();
}
