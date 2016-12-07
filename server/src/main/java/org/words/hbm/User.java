package org.words.hbm;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity
 * @author Eric Wang
 **/
@SuppressWarnings("unused")
public class User {
    private String id;
    private String name;
    private Integer version;
    private Set<Plan> plans = new HashSet<>();

    public User() {
        // default constructor
    }

    /**
     * Constructor with user name
     *
     * @param name user name
     */
    public User(String name) {
        this.name = name;
    }

    public Set<Plan> getPlans() {
        return plans;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    /**
     * Convenience method for bidirectional relation
     * @param plan plan entity
     */
    public void addPlan(Plan plan){
        plan.setUser(this);
        plans.add(plan);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return !(name != null ? !name.equals(user.name) : user.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

