package org.words.common;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.words.utils.HibernateUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by Eric on 2016/3/28.
 */
public class TranDelegator {
    private static Transaction transaction = null;

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method) throws Exception {
        boolean entry = false;
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null) {
            transaction = session.beginTransaction();
            entry = true;
        }
        try {
            Object returnVal = null;
            if (method.getReturnType() != null) {
                returnVal = superMethod.call();
            }
            transaction.commit();
            return returnVal;
        } catch (Exception e) {
            transaction.rollback();
            throw new IllegalStateException(e);
        } finally {
            if (entry) {
                transaction = null;
            }
        }
    }
}
