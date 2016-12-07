package org.words.to;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Plan entity
 * @author Eric Wang
 **/
public class PlanTO extends AbstractTO{
    private String id;
    private int version;
    private Date startDate;
    private int number;
    private UserTO user;
    private Set<TaskTO> tasks = new HashSet<>();

    /**
     * Constructor with startDate, number to learn
     *
     * @param startDate start date
     * @param number number to learn
     */
    public PlanTO(Date startDate, int number) {
        this.startDate = startDate;
        this.number = number;
    }

    @SuppressWarnings("unused")
    public PlanTO() { //hibernate use this
    }

    @SuppressWarnings("unused")
    public Set<TaskTO> getTasks() {
        return tasks;
    }

    @SuppressWarnings("unused")
    public void setTasks(Set<TaskTO> tasks) {
        this.tasks = tasks;
    }

    /**
     * Add task, convenice method for bidirectional relationship
     * @param task task to
     */
    @SuppressWarnings("unused")
    public void addTask(TaskTO task) {
        task.setPlan(this);
        tasks.add(task);
    }

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }

    @SuppressWarnings("unused")
    public int getNumber() {
        return number;
    }

    @SuppressWarnings("unused")
    public void setNumber(int number) {
        this.number = number;
    }

    @SuppressWarnings("unused")
    public Date getStartDate() {
        return startDate;
    }

    @SuppressWarnings("unused")
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
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PlanTO planTO = (PlanTO) o;

        //noinspection SimplifiableIfStatement
        if (number != planTO.number)
            return false;

        return !(startDate != null ? !startDate.equals(planTO.startDate) : planTO.startDate != null) && !(user != null ? !user.equals(planTO.user) : planTO.user != null);
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}

