package org.words.to;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity
 @author Eric Wang
 **/
@SuppressWarnings("unused")
public class UserTO extends AbstractTO {
    private String id;
    private String name;
    private Integer version;
    private Set<PlanTO> plans = new HashSet<>();

    public UserTO() { // intentionally blank
    }

    /**
     * Constructor, set name
     *
     * @param name user name
     */
    public UserTO(String name) {
        this.name = name;
    }

    public Set<PlanTO> getPlans() {
        return plans;
    }

    public void setPlans(Set<PlanTO> plans) {
        this.plans = plans;
    }

    /**
     * Convenience method for bidirectional relationship
     *
     * @param plan plan to
     */
    public void addPlan(PlanTO plan){
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

        UserTO userTO = (UserTO) o;

        return !(name != null ? !name.equals(userTO.name) : userTO.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

