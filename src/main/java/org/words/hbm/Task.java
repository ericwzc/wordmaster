package org.words.hbm; /**
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
public class Task {
    private String id;
    private int version;
    private Date deadLine;
    private Plan plan;
    private Set<Sentence> sentences = new HashSet<>();

    public Set<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(Set<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (deadLine != null ? !deadLine.equals(task.deadLine) : task.deadLine != null) return false;
        return !(plan != null ? !plan.equals(task.plan) : task.plan != null);

    }

    @Override
    public int hashCode() {
        int result = deadLine != null ? deadLine.hashCode() : 0;
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        return result;
    }
}

