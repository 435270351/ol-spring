package com.aspect;

import spring.aop.MethodInvocation;
import spring.common.annotation.After;
import spring.common.annotation.Around;
import spring.common.annotation.Aspect;
import spring.common.annotation.Before;

/**
 * aop测试
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
@Aspect
public class AspectDemo {

    //    @PointCut(value = "execution(* com.service.EnService.*(..))")
    final String pointCut = "execution(* com.service.EnService.*(..))";

    //    @PointCut(value = "execution(* com.service.HelloService.*(..))")
    final String pointCut2 = "execution(* com.service.HelloService.*(..))";

    @Before(value = pointCut)
    public void before() {
        System.out.println("Before");
    }

    @After(value = pointCut)
    public void after() {
        System.out.println("After");
    }

    @Around(value = pointCut)
    public void around(MethodInvocation methodInvocation) {
        System.out.println("Around Start");

        methodInvocation.invoke();

        System.out.println("Around End");
    }

    @Before(value = pointCut2)
    public void beforeHello() {
        System.out.println("hello before");
    }

}
