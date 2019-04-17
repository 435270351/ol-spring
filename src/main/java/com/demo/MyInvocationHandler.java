package com.demo;


import com.service.HelloService;
import com.service.HelloServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("~~~~before~~~~");

        Object val = method.invoke(target, args);

        System.out.println("~~~~after~~~~");

        return val;
    }

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();

        InvocationHandler handler = new MyInvocationHandler(helloService);

        HelloService service = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),HelloServiceImpl.class.getInterfaces(),handler);

        service.say();
    }

}
