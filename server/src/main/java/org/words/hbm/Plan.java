package org.words.hbm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Plan entity
 * @author Eric Wang
 **/
@SuppressWarnings("unused")
public class Plan {
    private String id;
    private int version;
    private Date startDate;
    private int number;
    private User user;
    private Set<Task> tasks = new HashSet<>();

    /**
     * Cosntructor with start date and learn number
     *
     * @param startDate start date
     * @param number number to learn
     */
    public Plan(Date startDate, int number) {
        this.startDate = startDate;
        this.number = number;
    }

    public Plan() {
        // used by hibernate
    }

    @SuppressWarnings("unused")
    public Set<Task> getTasks() {
        return tasks;
    }

    @SuppressWarnings("unused")
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Convenience method for bidirectional relation
     *
     * @param task task entity
     */
    @SuppressWarnings("unused")
    public void addTask(Task task) {
        task.setPlan(this);
        tasks.add(task);
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

    private void setId(String id) {//NOSONAR
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Plan plan = (Plan) o;

        if (number != plan.number)
            return false;
        //noinspection SimplifiableIfStatement
        if (startDate != null ? !startDate.equals(plan.startDate) : plan.startDate != null)
            return false;
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

