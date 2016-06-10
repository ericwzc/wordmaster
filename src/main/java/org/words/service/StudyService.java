package org.words.service;

import org.words.common.Transactional;
import org.words.to.SentenceTO;

import java.util.List;

/**
 * Study Service interface
 * @author Eric Wang
 **/
@SuppressWarnings("SpellCheckingInspection")
public interface StudyService {
    /**
     * Load number of tasks in alignment with param numbers
     *
     * @param newNum new words number
     * @param studiedNum review number
     *
     * @return list of Sentence TOs
     */
    @Transactional
    List<SentenceTO> loadTasks(int newNum, int studiedNum);

    /**
     * Mark familiarity up
     *
     * @param sentenceTO sentence to
     */
    @Transactional
    void familarityUp(SentenceTO sentenceTO);

    /**
     * Mark familiarity down
     *
     * @param sentenceTO sentence to
     */
    @SuppressWarnings("unused")
    @Transactional
    void familarityDown(SentenceTO sentenceTO);
}

