package org.words.to;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.common.Status;

/**
 * Record TO class
 *
 * @author Eric Wang
 **/
public class RecordTO extends AbstractTO{
    private String id;
    private int version;
    private Status status;
    private SentenceTO sentence;
    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SentenceTO getSentence() {
        return sentence;
    }

    public void setSentence(SentenceTO sentence) {
        sentence.addRecord(this);
        this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RecordTO record = (RecordTO) o;

        if (status != record.status)
            return false;
        return sentence.equals(record.sentence);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + sentence.hashCode();
        return result;
    }
}

