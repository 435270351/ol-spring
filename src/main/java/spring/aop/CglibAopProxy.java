package spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import service.En2ServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * CglibAopProxy代理
 *
 * @author tangzw
 * @date 2019-03-03
 * @since (版本)
 */
public class CglibAopProxy extends AbstractAopProxy {

    public CglibAopProxy(AdvisedSupport advisedSupport){
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
            MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();

            Object object;
            if (methodMatcher.matches(method)){
                System.out.println("before 被代理了");
                object = new CglibMethodInvocation(method,advisedSupport.getTargetSource(),objects).invoke();
                System.out.println("after 被代理了");
            }else {
                object = method.invoke(advisedSupport.getTargetSource().getTarget(),objects);
            }

            return object;
        }
    }

    private class CglibMethodInvocation{

        private Method method;

        private TargetSource targetSource;

        private Object[] params;

        public CglibMethodInvocation(Method method,TargetSource targetSource,Object[] params){
            this.method = method;
            this.targetSource = targetSource;
            this.params = params;
        }

        public Object invoke(){
            Object object = null;
            try {
                object = method.invoke(targetSource.getTarget(),params);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
