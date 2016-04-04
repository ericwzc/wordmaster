package org.words.to; /**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Task entity
 @author Eric Wang
 **/
public class TaskTO extends AbstractTO {
    private String id;
    private int version;
    private Date deadLine;
    private PlanTO plan;
    private Set<SentenceTO> sentences = new HashSet<>();

    public Set<SentenceTO> getSentences() {
        return sentences;
    }

    public void setSentences(Set<SentenceTO> sentences) {
        this.sentences = sentences;
    }

    public PlanTO getPlan() {
        return plan;
    }

    public void setPlan(PlanTO plan) {
        this.plan = plan;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addSentence(SentenceTO sentence){
        sentence.setTask(this);
        sentences.add(sentence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskTO task = (TaskTO) o;

        if (!deadLine.equals(task.deadLine)) return false;
        return plan.equals(task.plan);

    }

    @Override
    public int hashCode() {
        int result = deadLine.hashCode();
        result = 31 * result + plan.hashCode();
        return result;
    }
}

