package org.words.dao;

import org.words.hbm.Plan;
import org.words.to.TaskTO;
import org.words.utils.ConvertUtils;

/**
 * Created by Eric on 2016/3/19.
 */
public class PlanDao extends BaseDao<Plan> {
    public Plan getPlan(String userName) {
        Plan plan = (Plan) currentSession().createQuery("from Plan p where p.user.name = :name").setParameter("name", userName).list().get(0);
        return plan;
    }

    public void addTask(String planId, TaskTO taskTO) {
        //TODO
    }
}
