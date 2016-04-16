package org.words.service;

import org.words.common.Transactional;
import org.words.to.UserTO;

/**
 * Created by Eric on 2016/3/17.
 */
public interface UserService {
    @Transactional
    void saveUser(UserTO userTO);
}
