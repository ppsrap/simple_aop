package cn.lasse.springframework.aop.impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 用户自定义的拦截方法需要实现 MethodInterceptor 接口的 invoke 方法，
 * 使用方式与 Spring AOP 非常相似，也是包装 invocation.proceed() 放行，
 * 并在 finally 中添加监控信息。
 */
public class UserServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            System.out.println("我在这疯狂打印日志");
            return invocation.proceed();
        } finally {
            System.out.println("监控 - Begin By AOP");
            System.out.println("方法名称：" + invocation.getMethod());
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("监控 - End\r\n");
        }
    }
}