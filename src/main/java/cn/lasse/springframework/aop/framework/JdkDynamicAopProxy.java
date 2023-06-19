package cn.lasse.springframework.aop.framework;

import cn.lasse.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于 JDK 实现的代理类，需要实现接口 AopProxy、InvocationHandler，
 * 这样就可以把代理对象 getProxy 和反射调用方法 invoke 分开处理了。
 * @author Lasse
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 代理一个对象的操作，需要提供入参 ClassLoader、AdvisedSupport、和当前这个类 this，
     * 因为这个类提供了 invoke 方法。
     */
    @Override
    public Object getProxy() {
        Object instance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                advised.getTargetSource().getTargetClass(), this);
        return instance;
    }

    /**
     * 处理匹配的方法后，使用用户自己提供的方法拦截实现，
     * 做反射调用 methodInterceptor.invoke 。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = advised.getTargetSource().getTarget();
        if (advised.getMethodMatcher().matches(method, target.getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(target, method, args));
        }
        return method.invoke(target, args);
    }
}