package spring.aop;

import com.service.En2ServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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

    public CglibAopProxy(AdvisedSupport advisedSupport,TargetSource targetSource) {
        super(advisedSupport,targetSource);
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

    private class DynamicAdvisedInterceptor implements MethodInterceptor, OperationMethodInvocation {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            AdvisedSupport advisedSupport = getAdvisedSupport();

            MethodInvocation methodInvocation = new CglibMethodInvocation(method, getTargetSource(), objects);

            // 装饰环绕置通知
            List<ClassMatcher> aroundClassMatcherList = advisedSupport.getAroundClassMatcherList();
            methodInvocation = aroundMethodInvocation(method, methodInvocation, aroundClassMatcherList);

            // 装饰前置通知
            List<ClassMatcher> beforeClassMatcherList = advisedSupport.getBeforeClassMatcherList();
            methodInvocation = beforeMethodInvocation(method, methodInvocation, beforeClassMatcherList);

            // 装饰后置通知
            List<ClassMatcher> afterClassMatcherList = advisedSupport.getAfterClassMatcherList();
            methodInvocation = afterMethodInvocation(method, methodInvocation, afterClassMatcherList);


            Object object = methodInvocation.invoke();

            return object;
        }

        @Override
        public MethodInvocation beforeMethodInvocation(Method method, MethodInvocation target, List<ClassMatcher> classMatcherList) {
            MethodInvocation val = target;
            for (ClassMatcher classMatcher : classMatcherList) {
                if (classMatcher.matches(method)) {
                    BeforeMethodInvocation beforeMethodInvocation = new BeforeMethodInvocation(val);
                    val = beforeMethodInvocation;
                }
            }

            return val;
        }

        @Override
        public MethodInvocation aroundMethodInvocation(Method method, MethodInvocation target, List<ClassMatcher> classMatcherList) {
            MethodInvocation val = target;
            for (ClassMatcher classMatcher : classMatcherList) {
                if (classMatcher.matches(method)) {
                    AroundMethodInvocation aroundMethodInvocation = new AroundMethodInvocation(val);
                    val = aroundMethodInvocation;
                }
            }

            return val;
        }

        @Override
        public MethodInvocation afterMethodInvocation(Method method, MethodInvocation target, List<ClassMatcher> classMatcherList) {
            MethodInvocation val = target;
            for (ClassMatcher classMatcher : classMatcherList) {
                if (classMatcher.matches(method)) {
                    AfterMethodInvocation afterMethodInvocation = new AfterMethodInvocation(val);
                    val = afterMethodInvocation;
                }
            }

            return val;
        }
    }

    public static void main(String[] args) {
        TargetSource targetSource = new TargetSource();
        targetSource.setTargetClass(En2ServiceImpl.class);
        targetSource.setInterfaces(En2ServiceImpl.class.getInterfaces());

        AdvisedSupport advisedSupport = new AdvisedSupport();
//        advisedSupport.setTargetSource(targetSource);

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport,targetSource);
        En2ServiceImpl en2Service = (En2ServiceImpl) cglibAopProxy.getProxy();
        en2Service.ha();
    }

}
