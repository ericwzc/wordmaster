package org.words.config;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.words.common.TranDelegator;
import org.words.common.Transactional;
import org.words.dao.RecordDao;
import org.words.dao.SentenceDao;
import org.words.service.PlanService;
import org.words.service.StudyService;
import org.words.service.TaskService;
import org.words.service.UserService;
import org.words.service.impl.PlanServiceImpl;
import org.words.service.impl.StudyServiceImpl;
import org.words.service.impl.TaskServiceImpl;
import org.words.service.impl.UserServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * TODO The class ServiceModule is supposed to be documented...
 *
 * @author Eric Wang
 */
public class ServiceModule extends AbstractModule{
    // <editor-fold desc="static class">
    // </editor-fold>
    // <editor-fold desc="static constants">
    // </editor-fold>
    // <editor-fold desc="static method">
    // </editor-fold>
    // <editor-fold desc="instance data">
    // </editor-fold>
    // <editor-fold desc="constructor">
    // </editor-fold>
    // <editor-fold desc="public">
    // </editor-fold>
    // <editor-fold desc="private">
    public <T> Class<? extends T> transactionAware(Class<T> clazz, Object delegator) throws IllegalAccessException, InstantiationException {
       return new ByteBuddy().subclass(clazz, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
                .method(ElementMatchers.isAnnotatedWith(Transactional.class))
                .intercept(MethodDelegation.to(delegator))
                .make()
                .load(clazz.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }
    // </editor-fold>
    // <editor-fold desc="protected">

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    StudyService getStudyService(TranDelegator tranDelegator, SentenceDao sentenceDao, RecordDao recordDao) {
        try {
            Class<? extends StudyServiceImpl> aClass = transactionAware(StudyServiceImpl.class, tranDelegator);
            Constructor<? extends StudyServiceImpl> constructor = aClass.getConstructor(SentenceDao.class, RecordDao.class);
            return constructor.newInstance(sentenceDao, recordDao);
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }


    @Provides
    @Singleton
    TaskService getTaskService(TranDelegator tranDelegator){
        try {
            return transactionAware(TaskServiceImpl.class, tranDelegator).newInstance();
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        return null;
    }

    @Provides
    @Singleton
    PlanService getPlanService(TranDelegator tranDelegator){
        try {
            return transactionAware(PlanServiceImpl.class, tranDelegator).newInstance();
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        return null;
    }

    @Provides
    @Singleton
    UserService getUserService(TranDelegator tranDelegator){
        try {
            return transactionAware(UserServiceImpl.class, tranDelegator).newInstance();
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        return null;
    }
    // </editor-fold>
}

