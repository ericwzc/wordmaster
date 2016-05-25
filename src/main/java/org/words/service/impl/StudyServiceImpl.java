package org.words.service.impl;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.common.Status;
import org.words.common.Transactional;
import org.words.dao.RecordDao;
import org.words.dao.SentenceDao;
import org.words.hbm.Record;
import org.words.hbm.Sentence;
import org.words.service.StudyService;
import org.words.to.RecordTO;
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
    public List<SentenceTO> loadTasks(int newNum, int studiedNum) {
        List<SentenceTO> result = new ArrayList<>();
        if(newNum > 0) {
            List<Sentence> queryResult = sentenceDao.getNewSentences(newNum);

            List<Record> records = new ArrayList<>();

            for (Sentence sentence : queryResult) {
                Record record = new Record();
                record.setSentence(sentence);
                records.add(record);
            }

            recordDao.saveRecords(records);
        }

        addRecord(result, recordDao.loadNewRecords());

        // query for to review ones
        if(studiedNum > 0)
            addRecord(result, recordDao.load2ReviewRecords(studiedNum));

        return result;
    }

    @Transactional
    @Override
    public RecordTO familarityUp(SentenceTO sentenceTO) {
        Record record = recordDao.get(Record.class, sentenceTO.getRecord().iterator().next().getId());
        record.setStatus(Status.STUDIED);
        record.setCounter(record.getCounter() + 1);
        return ConvertUtils.convert(record, RecordTO.class);
    }

    private void addRecord(List<SentenceTO> tos, List<Record> entities){
       for(Record record : entities) {
          tos.add(ConvertUtils.convert(record.getSentence(), SentenceTO.class));
       }
    }
}

