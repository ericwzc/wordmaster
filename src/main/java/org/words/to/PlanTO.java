package org.words.to;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Plan entity
 @author Eric Wang
 **/
public class PlanTO extends AbstractTO{
    private String id;
    private int version;
    private Date startDate;
    private int number;
    private UserTO user;
    private Set<TaskTO> tasks = new HashSet<>();

    public Set<TaskTO> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskTO> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TaskTO task) {
        task.setPlan(this);
        tasks.add(task);
    }

    public PlanTO(Date startDate, int number) {
        this.startDate = startDate;
        this.number = number;
    }

    public PlanTO() {
    }

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

        PlanTO planTO = (PlanTO) o;

        if (number != planTO.number) return false;
        if (startDate != null ? !startDate.equals(planTO.startDate) : planTO.startDate != null) return false;
        return !(user != null ? !user.equals(planTO.user) : planTO.user != null);

    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}

