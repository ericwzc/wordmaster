package org.words.hbm;
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
public class Plan {
    private String id;
    private int version;
    private Date startDate;
    private int number;
    private User user;
    private Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        task.setPlan(this);
        tasks.add(task);
    }

    public Plan(Date startDate, int number) {
        this.startDate = startDate;
        this.number = number;
    }

    public Plan() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    private void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plan plan = (Plan) o;

        if (number != plan.number) return false;
        if (startDate != null ? !startDate.equals(plan.startDate) : plan.startDate != null) return false;
        return !(user != null ? !user.equals(plan.user) : plan.user != null);

    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}

