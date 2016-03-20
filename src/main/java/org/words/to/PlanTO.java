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
 * Plan Transfer Object
 @author Eric Wang
 **/
public class PlanTO extends AbstractTO {
    private String id;
    private Integer version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

