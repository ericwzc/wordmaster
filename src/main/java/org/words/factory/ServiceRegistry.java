package org.words.factory;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.words.common.TranDelegator;
import org.words.common.Transactional;
import org.words.service.PlanService;
import org.words.service.StudyService;
import org.words.service.TaskService;
import org.words.service.UserService;
import org.words.service.impl.PlanServiceImpl;
import org.words.service.impl.StudyServiceImpl;
import org.words.service.impl.TaskServiceImpl;
import org.words.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Service registry, to be replaced by dependency injection
 */
public class ServiceRegistry {

    private static Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

    private static Map<Class<?>, Object> registry = new HashMap<>();

    static {
        register(UserService.class, UserServiceImpl.class);
        register(PlanService.class, PlanServiceImpl.class);
        register(TaskService.class, TaskServiceImpl.class);
        register(StudyService.class, StudyServiceImpl.class);
    }

    private ServiceRegistry() {}

    private static <T> void register(Class<T> ret, Class<? extends T> implClazz) {
        try {
            registry.put(ret, transactionAware(implClazz).newInstance());
        } catch (InstantiationException | IllegalAccessException ignored) {
            logger.error("error in initialisation service: {}", ignored);
        }
    }

    private static <T> Class<? extends T> transactionAware(Class<T> clazz) {
        return new ByteBuddy().subclass(clazz).method(ElementMatchers.isAnnotatedWith(Transactional.class))
                .intercept(MethodDelegation.to(TranDelegator.class))
                .make()
                .load(clazz.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }

    /**
     * Get service instance by interface name
     * @param tClass interface name
     * @param <T> class type
     *
     * @return registered implementation instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getServiceInstance(Class<T> tClass) {
        return (T) registry.get(tClass);
    }
}
