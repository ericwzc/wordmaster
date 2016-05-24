package org.words.service;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.common.Transactional;
import org.words.to.SentenceTO;

import java.util.List;

/**
 * Study Service interface
 @author Eric Wang
 **/
public interface StudyService {
    @Transactional
    List<SentenceTO> loadTasks(int newNum, int revNum);
}

