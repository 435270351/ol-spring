package spring.aop;

import com.service.HelloService;
import com.service.HelloServiceImpl;
import spring.aop.invocation.MethodInvocation;
import spring.aop.invocation.ReflectiveMethodInvocation;
import spring.ioc.factory.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * jdk代理
 *
 * @author tangzw
 * @date 2019-04-16
 * @since 1.0.0
 */
public class JdkAopProxy implements InvocationHandler, AopProxy {

    private AdvisedSupport advisedSupport;

    public JdkAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        TargetSource targetSource = advisedSupport.getTargetSource();

        return Proxy.newProxyInstance(targetSource.getTargetClass().getClassLoader(), targetSource.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取通知链
        List<Object> chainList = advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(method);

        MethodInvocation methodInvocation = new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args, chainList);
        Object object = methodInvocation.proceed();

        return object;
    }
}
