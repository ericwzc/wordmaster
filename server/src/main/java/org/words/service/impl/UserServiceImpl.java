package org.words.service.impl;

import com.google.inject.Inject;
import org.words.common.Transactional;
import org.words.dao.UserDao;
import org.words.hbm.User;
import org.words.service.UserService;
import org.words.to.UserTO;
import org.words.utils.ConvertUtils;

/**
 * User service impl
 */
public class UserServiceImpl implements UserService{

    @Inject
    UserDao userDao;
                
    @Transactional
    @Override
    public void saveUser(UserTO userTO) {
        userDao.save(ConvertUtils.convert(userTO, User.class));
    }
}
