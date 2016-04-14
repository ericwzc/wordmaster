package org.words.service.impl;

import org.words.common.Transactional;
import org.words.dao.SentenceDao;
import org.words.hbm.Sentence;
import org.words.service.TaskService;
import org.words.to.SentenceTO;
import org.words.to.TaskTO;
import org.words.utils.ConvertUtils;

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
        List<SentenceTO> tos = new ArrayList<>(entityList.size());
        for(Sentence sentence : entityList){
            tos.add(ConvertUtils.convert(sentence, SentenceTO.class));
        }
        return tos;
    }
}
