package org.words.service.impl;

import org.words.common.Transactional;
import org.words.dao.PlanDao;
import org.words.hbm.Plan;
import org.words.service.PlanService;
import org.words.to.PlanTO;
import org.words.utils.ConvertUtils;

/**
 * Created by Eric on 2016/4/4.
 */
public class PlanServiceImpl implements PlanService {

    private PlanDao planDao = new PlanDao();

    @Transactional
    @Override
    public PlanTO getPlan(String userName) {
        Plan plan = planDao.getPlan(userName);
        return ConvertUtils.convert(plan, PlanTO.class);
    }
}
