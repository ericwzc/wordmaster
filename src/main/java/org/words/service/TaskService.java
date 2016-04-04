package org.words.service;

import org.words.common.Transactional;
import org.words.hbm.Task;
import org.words.to.SentenceTO;
import org.words.to.TaskTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2016/3/17.
 */
public interface TaskService {
    @Transactional
    List<TaskTO> getTasks(Date date);
    @Transactional
    void createTasks(Collection<TaskTO> tasks);
    @Transactional
    List<SentenceTO> getSentences();
}
