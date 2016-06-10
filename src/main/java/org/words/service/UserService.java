package org.words.service;

import org.words.common.Transactional;
import org.words.to.UserTO;

/**
 * User service interface
 */
public interface UserService {//NOSONAR

    /**
     * Save user
     * @param userTO user to
     */
    @Transactional
    void saveUser(UserTO userTO);
}
