package org.words.service;

import org.words.common.Transactional;
import org.words.to.SentenceTO;

import java.util.List;

/**
 * Task service interface
 */
public interface TaskService {//NOSONAR

    /**
     * Load setences for today
     * @return list of sentence tos
     */
    @SuppressWarnings("unused")
    @Transactional
    List<SentenceTO> getSentences4Today();
}
