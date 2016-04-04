package org.words.to;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import java.util.HashSet;
import java.util.Set;

/**
 * User entity
 @author Eric Wang
 **/
public class UserTO extends AbstractTO {
    private String id;
    private String name;
    private Integer version;
    private Set<PlanTO> plans = new HashSet<>();

    public UserTO() {
    }

    public UserTO(String name) {
        this.name = name;
    }

    public Set<PlanTO> getPlans() {
        return plans;
    }

    public void setPlans(Set<PlanTO> plans) {
        this.plans = plans;
    }

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
}

