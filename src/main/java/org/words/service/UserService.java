package org.words.service;

import org.words.hbm.User;

/**
 * Created by Eric on 2016/3/17.
 */
public interface UserService {
    void saveUser();
    User getUser();
}
