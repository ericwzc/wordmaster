package org.words.service.impl;

import com.google.inject.Inject;
import org.words.common.Transactional;
import org.words.dao.TaskDao;
import org.words.hbm.Sentence;
import org.words.hbm.Task;
import org.words.service.TaskService;
import org.words.to.SentenceTO;
import org.words.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Task service implementation
 */
public class TaskServiceImpl implements TaskService {

    @Inject
    TaskDao taskDao;

    @Transactional
    @Override
    public List<SentenceTO> getSentences4Today() {
        List<Sentence> entityList = new ArrayList<>(getTask4Today().getSentences());

        List<SentenceTO> tos = new ArrayList<>(entityList.size());

        for (Sentence sentence : entityList)
            tos.add(ConvertUtils.convert(sentence, SentenceTO.class));

        return tos;
    }


    private Task getTask4Today() {
        return taskDao.getTaskForDate(new Date());
    }
}
