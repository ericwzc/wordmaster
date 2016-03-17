package org.words.hbm;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

/**
 * User entity
 @author Eric Wang
 **/
public class User {
    private String id;
    private String name;

    private Plan oneToOne;

    private Integer version;

    private Plan plan;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
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

    public Plan getOneToOne() {
        return oneToOne;
    }

    public void setOneToOne(Plan oneToOne) {
        this.oneToOne = oneToOne;
    }
}

