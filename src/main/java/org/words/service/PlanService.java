package org.words.service;

import org.words.common.Transactional;
import org.words.hbm.Plan;
import org.words.to.PlanTO;

/**
 * Created by Eric on 2016/3/17.
 */
public interface PlanService {
    @Transactional
    PlanTO getPlan();
    @Transactional
    void savePlan();
    @Transactional
    void updatePlan(PlanTO planTO);
}
