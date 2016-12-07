package org.words.service;

import org.words.to.PlanTO;

/**
 * Plan service interface
 */
public interface PlanService {//NOSONAR
    /**
     * Get the first plan only
     * @param userName user name
     * @return first Plan
     */
    @SuppressWarnings("unused")
    PlanTO getPlan(String userName);
}
