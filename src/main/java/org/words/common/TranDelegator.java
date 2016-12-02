package org.words.common;

import com.google.inject.Inject;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Simple transaction enhancement via byte code manipulation, only supports Spring required
 */
public class TranDelegator {
    private Transaction transaction;

    @Inject
    SessionFactory sessionFactory;

    /**
     * Byte code intercept magic
     * @param superMethod user method
     * @param method original bean method
     *
     * @return return value from superMethod call
     * @throws Exception
     */
    @RuntimeType
    public Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method) throws Exception {//NOSONAR
        boolean entry = false;
        Session session = sessionFactory.getCurrentSession();
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
