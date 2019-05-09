package com.aspect;

import spring.aop.invocation.MethodInvocation;
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

    final String pointCut = "execution(* com.service..*(..))";

    @Before(value = pointCut)
    public void before() {
        System.out.println("Before");
    }

    @After(value = pointCut)
    public void after() {
        System.out.println("After");
    }

    @Around(value = pointCut)
    public void around(MethodInvocation methodInvocation) throws Exception {
        System.out.println("Around Start");

        methodInvocation.proceed();

        System.out.println("Around End");
    }

}
