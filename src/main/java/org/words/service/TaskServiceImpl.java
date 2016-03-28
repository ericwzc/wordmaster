package org.words.service;

import org.apache.commons.beanutils.BeanUtils;
import org.words.common.Transactional;
import org.words.dao.SentenceDao;
import org.words.hbm.Task;
import org.words.to.SentenceTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2016/3/28.
 */
public class TaskServiceImpl implements TaskService{

    @Override
    public List<Task> getTasks(Date date) {
        return null;
    }

    @Override
    public void createTasks(Collection<Task> tasks) {

    }

    @Transactional
    @Override
    public List<SentenceTO> getSentences() {
        return new SentenceDao().getSentences();
    }
}
