package org.words.service.impl;

import org.words.common.Transactional;
import org.words.hbm.Plan;
import org.words.service.PlanService;
import org.words.to.PlanTO;

/**
 * Created by Eric on 2016/4/4.
 */
public class PlanServiceImpl implements PlanService{
    @Transactional
    @Override
    public PlanTO getPlan() {
        return null;
    }

    @Transactional
    @Override
    public void savePlan() {

    }

    @Transactional
    @Override
    public void updatePlan(PlanTO plan) {

    }
}
