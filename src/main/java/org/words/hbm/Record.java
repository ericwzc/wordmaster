package org.words.hbm;

import org.words.common.Status;

/**
 * RecordTO entity class
 *
 * @author Eric Wang
 **/
public class Record {
    private String id;
    private int version;
    private Status status;
    private Sentence sentence;
    private int counter;

    /**
     * Default contructor with default status
     */
    public Record(){
        status = Status.NEW;
    }

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

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        sentence.getRecord().add(this);
        this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Record record = (Record) o;

        return status == record.status && sentence.equals(record.sentence);

    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        return result;
    }
}

