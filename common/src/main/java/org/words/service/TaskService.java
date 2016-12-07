package org.words.service;

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
    List<SentenceTO> getSentences4Today();
}
