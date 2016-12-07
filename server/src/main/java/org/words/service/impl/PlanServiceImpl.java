package org.words.service.impl;

import com.google.inject.Inject;
import org.words.common.Transactional;
import org.words.dao.PlanDao;
import org.words.hbm.Plan;
import org.words.service.PlanService;
import org.words.to.PlanTO;
import org.words.utils.ConvertUtils;

/**
 * Plan service implementation
 */
public class PlanServiceImpl implements PlanService {

    @Inject
    private PlanDao planDao;
                       
    @Transactional
    @Override
    public PlanTO getPlan(String userName) {
        Plan plan = planDao.getPlan(userName);
        return ConvertUtils.convert(plan, PlanTO.class);
    }           
}
