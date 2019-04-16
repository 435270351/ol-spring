package spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import spring.aop.invocation.MethodInvocation;
import spring.aop.invocation.ReflectiveMethodInvocation;
import spring.ioc.factory.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

/**
 * CglibAopProxy代理
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class CglibAopProxy implements AopProxy {

    private AdvisedSupport advisedSupport;

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        TargetSource targetSource = advisedSupport.getTargetSource();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetSource.getTargetClass());
        enhancer.setInterfaces(targetSource.getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor());

        return enhancer.create();
    }

    private class DynamicAdvisedInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            // 获取通知链
            List<Object> chainList = advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(method);

            MethodInvocation methodInvocation = new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, objects, chainList);
            Object object = methodInvocation.proceed();

            return object;
        }

    }

}
