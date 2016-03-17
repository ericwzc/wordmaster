package org.words.service;

import org.words.hbm.Plan;

/**
 * Created by Eric on 2016/3/17.
 */
public interface PlanService {
    Plan getPlan();
    void savePlan();
    void updatePlan(Plan plan);
}
