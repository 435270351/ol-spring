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

    private class DynamicAdvisedInterceptor implements MethodInterceptor, OperationMethodInvocation {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            AdvisedSupport advisedSupport = getAdvisedSupport();

            MethodInvocation methodInvocation = new TargetMethodInvocation(method, getTargetSource(), objects);

            // 装饰环绕置通知
            List<AopMethod> aroundAopMethodList = advisedSupport.getAroundAopMethod();
            methodInvocation = aroundMethodInvocation(method, methodInvocation, aroundAopMethodList);

            // 装饰前置通知
            List<AopMethod> beforeAopMethodList = advisedSupport.getBeforeAopMethod();
            methodInvocation = beforeMethodInvocation(method, methodInvocation, beforeAopMethodList);

            // 装饰后置通知
            List<AopMethod> afterAopMethodList = advisedSupport.getAfterAopMethod();
            methodInvocation = afterMethodInvocation(method, methodInvocation, afterAopMethodList);

            Object object = methodInvocation.invoke();

            return object;
        }

        @Override
        public MethodInvocation beforeMethodInvocation(Method method, MethodInvocation target, List<AopMethod> aopMethodList) {
            MethodInvocation val = target;
            for (AopMethod aopMethod : aopMethodList) {
                if (aopMethod.getClassMatcher().matches(method)) {
                    BeforeMethodInvocation beforeMethodInvocation = new BeforeMethodInvocation(val, aopMethod);
                    val = beforeMethodInvocation;
                }
            }

            return val;
        }

        @Override
        public MethodInvocation aroundMethodInvocation(Method method, MethodInvocation target, List<AopMethod> aopMethodList) {
            MethodInvocation val = target;
            for (AopMethod aopMethod : aopMethodList) {
                if (aopMethod.getClassMatcher().matches(method)) {
                    AroundMethodInvocation aroundMethodInvocation = new AroundMethodInvocation(val, aopMethod);
                    val = aroundMethodInvocation;
                }
            }

            return val;
        }

        @Override
        public MethodInvocation afterMethodInvocation(Method method, MethodInvocation target, List<AopMethod> aopMethodList) {
            MethodInvocation val = target;

            for (AopMethod aopMethod : aopMethodList) {
                if (aopMethod.getClassMatcher().matches(method)) {
                    AfterMethodInvocation afterMethodInvocation = new AfterMethodInvocation(val, aopMethod);
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

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport, targetSource);
        En2ServiceImpl en2Service = (En2ServiceImpl) cglibAopProxy.getProxy();
        en2Service.ha();
    }

}
