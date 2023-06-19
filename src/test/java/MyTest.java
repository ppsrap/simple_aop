import cn.lasse.springframework.aop.AdvisedSupport;
import cn.lasse.springframework.aop.TargetSource;
import cn.lasse.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.lasse.springframework.aop.framework.Cglib2AopProxy;
import cn.lasse.springframework.aop.framework.JdkDynamicAopProxy;
import cn.lasse.springframework.aop.IUserService;
import cn.lasse.springframework.aop.impl.UserServiceImpl;

import java.lang.reflect.Method;
import org.junit.Test;
import cn.lasse.springframework.aop.impl.UserServiceInterceptor;

public class MyTest {

    @Test
    public void testAopMatches() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* cn.lasse.springframework.aop.impl.UserServiceImpl.*(..))");
        Class<UserServiceImpl> clazz = UserServiceImpl.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
        // true、true
    }

    @Test
    public void testDynamic() {
        // 目标对象
        IUserService userService = new UserServiceImpl();

        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.lasse.springframework.aop.IUserService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxyJdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxyJdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxyCglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxyCglib.register("花花"));
    }
}
