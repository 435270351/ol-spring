package com.aspect;

import spring.annotation.After;
import spring.annotation.Around;
import spring.annotation.Aspect;
import spring.annotation.Before;
import spring.annotation.PointCut;
import spring.aop.MethodInvocation;

/**
 * aop测试
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
@Aspect
public class AspectDemo {

    @PointCut(value = "execution(* com.service.EnService.*(..))")
    String pointCut;

    @PointCut(value = "execution(* com.service.HelloService.*(..))")
    String pointCut2;

    @Before(value = "pointCut")
    public void before(){
        System.out.println("Before");
    }

    @After(value = "pointCut")
    public void after(){
        System.out.println("After");
    }

    @Around(value = "pointCut")
    public void around(MethodInvocation methodInvocation){
        System.out.println("Around Start");

        methodInvocation.invoke();

        System.out.println("Around End");
    }

    @Before(value = "pointCut2")
    public void beforeHello(){
        System.out.println("hello before");
    }

}
