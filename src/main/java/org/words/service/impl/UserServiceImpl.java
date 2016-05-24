package org.words.service.impl;

import org.words.common.Transactional;
import org.words.dao.UserDao;
import org.words.hbm.User;
import org.words.service.UserService;
import org.words.to.UserTO;
import org.words.utils.ConvertUtils;

/**
 * Created by Eric on 2016/4/4.
 */
public class UserServiceImpl implements UserService{

    private UserDao userDao = new UserDao();

    @Transactional
    @Override
    public void saveUser(UserTO userTO) {
        userDao.save(ConvertUtils.convert(userTO, User.class));
    }
}