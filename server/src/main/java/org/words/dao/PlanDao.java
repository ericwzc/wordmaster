package org.words.dao;

import com.google.inject.Singleton;
import org.words.hbm.Plan;

/**
 * Plan dao class
 */
@Singleton
public class PlanDao extends BaseDao<Plan> {
    /**
     * Get plan for specified user
     * @param userName user name
     *
     * @return plan 1
     */
    public Plan getPlan(String userName) {
        //noinspection JpaQlInspection
        return (Plan) currentSession().createQuery("from Plan p where p.user.name = :name").setParameter("name", userName).list().get(0);
    }
}
