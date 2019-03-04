package spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import service.En2ServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * CglibAopProxy代理
 *
 * @author tangzw
 * @date 2019-03-03
 * @since (版本)
 */
public class CglibAopProxy extends AbstractAopProxy {

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        super(advisedSupport);
    }

    @Override
    public Object getProxy() {
        TargetSource targetSource = getAdvisedSupport().getTargetSource();

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
            List<MethodMatcher> methodMatcherList = advisedSupport.getMethodMatcherList();

            Object object = null;
//            if (methodMatcher.matches(method)) {
//                System.out.println("before 被代理了");
//                object = method.invoke(advisedSupport.getTargetSource().getTarget(), objects);
//                System.out.println("after 被代理了");
//            } else {
//                object = method.invoke(advisedSupport.getTargetSource().getTarget(), objects);
//            }

            return object;
        }
    }


    public static void main(String[] args) {
        TargetSource targetSource = new TargetSource();
        targetSource.setTargetClass(En2ServiceImpl.class);
        targetSource.setInterfaces(En2ServiceImpl.class.getInterfaces());

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport);
        En2ServiceImpl en2Service = (En2ServiceImpl) cglibAopProxy.getProxy();
        en2Service.ha();
    }

}
