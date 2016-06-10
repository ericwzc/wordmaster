package org.words.dao;

import org.words.hbm.User;

/**
 * User dao class
 */
public class UserDao extends BaseDao<User>{
    /**
     * Get user by name
     * @param name name
     *
     * @return User Entity
     */
   @SuppressWarnings("unused")
   public User getByName(String name){
       //noinspection JpaQlInspection
       return (User) currentSession().createQuery("select u from User u join fetch u.plans  where u.name = :name").setParameter("name", name).list().get(0);
   }
}
