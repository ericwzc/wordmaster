/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import org.words.service.PlanService;
import org.words.to.PlanTO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * TODO The class App is supposed to be documented...
 *
 * @author Eric Wang
 */
public class App {
    // <editor-fold desc="static class">
    // </editor-fold>
    // <editor-fold desc="static constants">
    // </editor-fold>
    // <editor-fold desc="static method">
    public static void main(String[] args) {
        PlanService planService = (PlanService) Proxy.newProxyInstance(App.class.getClassLoader(), new Class[] { PlanService.class }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("called with args:" + Arrays.toString(args));
                PlanTO planTO = new PlanTO();
                planTO.setId("1234");
                return planTO;
            }
        });

        System.out.println(planService.getPlan("123").getId());
    }
    // </editor-fold>
    // <editor-fold desc="instance data">
    // </editor-fold>
    // <editor-fold desc="constructor">
    // </editor-fold>
    // <editor-fold desc="public">

    // </editor-fold>
    // <editor-fold desc="private">
    // </editor-fold>
    // <editor-fold desc="protected">
    // </editor-fold>
}

