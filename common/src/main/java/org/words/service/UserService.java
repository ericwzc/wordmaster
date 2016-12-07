package org.words.service;

import org.words.to.UserTO;

/**
 * User service interface
 */
public interface UserService {//NOSONAR

    /**
     * Save user
     * @param userTO user to
     */
    void saveUser(UserTO userTO);
}
