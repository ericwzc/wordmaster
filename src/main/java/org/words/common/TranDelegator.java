package org.words.common;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.words.utils.HibernateUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Created by Eric on 2016/3/28.
 */
public class TranDelegator {
    private static Session session = null;
    private static Transaction transaction = null;

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method) throws Exception {
        boolean entry = false;
        if (session == null) {
            session = HibernateUtils.getSessionFactory().getCurrentSession();
        }
        if (transaction == null) {
            transaction = session.beginTransaction();
            entry = true;
        }
        try {
            if (method.getReturnType() != null) {
                return superMethod.call();
            } else {
                superMethod.call();
                return null;
            }
        } catch (Exception e) {
            if (entry) {
                transaction.rollback();
            }
            throw new IllegalStateException(e);
        } finally {
            if (entry) {
                transaction.commit();
                transaction = null;
                session = null;
            }
        }
    }
}
