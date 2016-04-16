package org.words.service;

import org.words.common.Transactional;
import org.words.to.PlanTO;

/**
 * Created by Eric on 2016/3/17.
 */
public interface PlanService {
    /**
     * Get the first plan only
     * @param userName
     * @return
     */
    @Transactional
    PlanTO getPlan(String userName);
}
