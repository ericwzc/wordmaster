package org.words.service.impl;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.common.Transactional;
import org.words.dao.RecordDao;
import org.words.dao.SentenceDao;
import org.words.hbm.Record;
import org.words.hbm.Sentence;
import org.words.service.StudyService;
import org.words.to.SentenceTO;
import org.words.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class for study service
 *
 * @author Eric Wang
 **/
public class StudyServiceImpl implements StudyService {

    private SentenceDao sentenceDao = new SentenceDao();
    private RecordDao recordDao = new RecordDao();

    @Transactional
    @Override
    public List<SentenceTO> loadTasks(int newNum, int revNum) {
        List<SentenceTO> result = new ArrayList<>();
        List<Sentence> queryResult = sentenceDao.getNewSentences(newNum);

        List<Record> records = new ArrayList<>();

        for(Sentence sentence : queryResult){
            Record record = new Record();
            record.setSentence(sentence);
            result.add(ConvertUtils.convert(sentence, SentenceTO.class));
        }

        recordDao.saveRecords(records);

        // query for to review ones

        for(Record record : recordDao.load2ReviewRecords(revNum)){
            result.add(ConvertUtils.convert(record.getSentence(), SentenceTO.class));
        }

        return result;
    }
}

