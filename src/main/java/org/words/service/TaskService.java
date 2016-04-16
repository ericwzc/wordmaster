package org.words.service;

import org.words.common.Transactional;
import org.words.to.SentenceTO;
import org.words.to.TaskTO;

import java.util.List;

/**
 * Created by Eric on 2016/3/17.
 */
public interface TaskService {
    @Transactional
    List<SentenceTO> getSentences4Today();
}
