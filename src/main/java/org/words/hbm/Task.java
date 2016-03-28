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
