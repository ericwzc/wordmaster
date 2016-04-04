package org.words.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.words.common.Transactional;
import org.words.dao.SentenceDao;
import org.words.hbm.Sentence;
import org.words.hbm.Task;
import org.words.service.TaskService;
import org.words.to.SentenceTO;
import org.words.to.TaskTO;
import org.words.utils.BeanConverters;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2016/3/28.
 */
public class TaskServiceImpl implements TaskService {

    @Transactional
    @Override
    public List<TaskTO> getTasks(Date date) {
        return null;
    }

    @Transactional
    @Override
    public void createTasks(Collection<TaskTO> tasks) {

    }

    @Transactional
    @Override
    public List<SentenceTO> getSentences() {
        List<Sentence> entityList = new SentenceDao().getSentences();
        return BeanConverters.convert(entityList, new ArrayList<SentenceTO>());
    }
}
