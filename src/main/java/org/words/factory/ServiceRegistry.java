package org.words.factory;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.words.common.TranDelegator;
import org.words.common.Transactional;
import org.words.service.PlanService;
import org.words.service.TaskService;
import org.words.service.impl.PlanServiceImpl;
import org.words.service.impl.TaskServiceImpl;
import org.words.service.UserService;
import org.words.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2016/3/28.
 */
public class ServiceRegistry {

    private static <T> Class<? extends T> transactionAware (Class<T> clazz){
        return new ByteBuddy().subclass(clazz).method(ElementMatchers.isAnnotatedWith(Transactional.class))
                .intercept(MethodDelegation.to(TranDelegator.class))
                .make()
                .load(clazz.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }

    private static UserService userService;
    private static PlanService planService;
    private static TaskService taskService;

    private static Map<Class<?>, Object> registry = new HashMap<>();

    static {
        try {
            userService = transactionAware(UserServiceImpl.class).newInstance();
            planService = transactionAware(PlanServiceImpl.class).newInstance();
            taskService = transactionAware(TaskServiceImpl.class).newInstance();
            registry.put(UserService.class, userService);
            registry.put(PlanService.class, planService);
            registry.put(TaskService.class, taskService);
            registry = Collections.unmodifiableMap(registry);// readonly once initialized
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getServiceInstance(Class<T> tClass){
        return (T) registry.get(tClass);
    }
}
