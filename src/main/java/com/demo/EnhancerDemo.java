package com.demo;

import com.service.HelloServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class EnhancerDemo {

    static int index = 0;
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        HelloServiceImpl demo = (HelloServiceImpl) enhancer.create();
        demo.say();
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args,
                MethodProxy proxy) throws Throwable {
            if (index==2){
                return "S";
            }
            index++;
            System.out.println("Before invoke " + method);
            Object result = proxy.invoke(obj, args);
            System.out.println("After invoke" + method);


            return result;
        }
    }
}
