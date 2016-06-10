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
 * Simple transaction enhancement via byte code manipulation, no nested transaction support
 */
public class TranDelegator {
    private static Transaction transaction = null;

    private TranDelegator(){} //noinspection

    /**
     * Byte code intercept magic
     * @param superMethod user method
     * @param method original bean method
     *
     * @return return value from superMethod call
     * @throws Exception
     */
    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method) throws Exception {//NOSONAR
        boolean entry = false;
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (transaction == null) {
            transaction = session.beginTransaction();
            entry = true;
        }
        try {
            Object returnVal = null;
            if (method.getReturnType() != null)
                returnVal = superMethod.call();

            transaction.commit();
            return returnVal;
        } catch (Exception e) {
            transaction.rollback();
            throw new IllegalStateException(e);
        } finally {
            if (entry)
                transaction = null;
        }
    }
}
