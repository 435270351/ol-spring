package spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import spring.aop.invocation.MethodInvocation;
import spring.aop.invocation.ReflectiveMethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

/**
 * CglibAopProxy代理
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class CglibAopProxy extends AbstractAopProxy {

    public CglibAopProxy(AdvisedSupport advisedSupport, TargetSource targetSource) {
        super(advisedSupport, targetSource);
    }

    @Override
    public Object getProxy() {
        TargetSource targetSource = getTargetSource();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetSource.getTargetClass());
        enhancer.setInterfaces(targetSource.getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor());

        return enhancer.create();
    }

    private class DynamicAdvisedInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            AdvisedSupport advisedSupport = getAdvisedSupport();

            // 获取通知链
            List<Advisor> chainList = advisedSupport.getChainList(method);

            if (chainList.isEmpty()) {
                method.invoke(o, objects);
            }

            MethodInvocation methodInvocation = new ReflectiveMethodInvocation(chainList);
            Object object = methodInvocation.proceed();

            return object;
        }

    }

}
