package org.words.to;

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

    @SuppressWarnings("unused")
    public int getCounter() {
        return counter;
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Status getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
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

        return status == record.status && sentence.equals(record.sentence);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + sentence.hashCode();
        return result;
    }
}

