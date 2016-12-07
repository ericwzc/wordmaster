package org.words.to;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Task entity
 * @author Eric Wang
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

    @SuppressWarnings("unused")
    public Date getDeadLine() {
        return deadLine;
    }

    @SuppressWarnings("unused")
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
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TaskTO taskTO = (TaskTO) o;

        return !(deadLine != null ? !deadLine.equals(taskTO.deadLine) : taskTO.deadLine != null) && !(plan != null ? !plan.equals(taskTO.plan) : taskTO.plan != null);

    }

    @Override
    public int hashCode() {
        int result = deadLine != null ? deadLine.hashCode() : 0;
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        return result;
    }
}

