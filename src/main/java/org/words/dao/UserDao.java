package org.words.dao;

import org.words.hbm.User;

/**
 * Created by Eric on 2016/3/19.
 */
public class UserDao extends BaseDao<User>{
   public User getByName(String name){
      return (User) currentSession().createQuery("select u from User u join fetch u.plans  where u.name = :name").setParameter("name", name).list().get(0);
   }
}
