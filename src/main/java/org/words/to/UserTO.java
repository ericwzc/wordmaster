package org.words.to;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

/**
 * User Transfer Object
 @author Eric Wang
 **/
public class UserTO extends AbstractTO {
    private String id;
    private String name;

    private PlanTO oneToOne;

    private Integer version;

    private PlanTO plan;

    public UserTO() {
    }

    public UserTO(String name) {
        this.name = name;
    }

    public PlanTO getPlan() {
        return plan;
    }

    public void setPlan(PlanTO plan) {
        this.plan = plan;
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

    public PlanTO getOneToOne() {
        return oneToOne;
    }

    public void setOneToOne(PlanTO oneToOne) {
        this.oneToOne = oneToOne;
    }
}

