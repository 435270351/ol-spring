package com.demo;

import com.service.HelloServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-03-02
 * @since (版本)
 */
public class EnhancerDemo {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        HelloServiceImpl demo = (HelloServiceImpl) enhancer.create();
        demo.say();
//        System.out.println(demo.getName());
    }

    public void test() {
        System.out.println("EnhancerDemo test()");
    }


    private static class MethodInterceptorImpl implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args,
                MethodProxy proxy) throws Throwable {
            System.err.println("Before invoke " + method);
            Object result = proxy.invokeSuper(obj, args);
            System.err.println("After invoke" + method);
            return result;
        }
    }
}
