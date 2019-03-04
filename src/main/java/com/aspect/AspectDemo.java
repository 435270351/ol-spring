package com.aspect;

import spring.annotation.Aspect;
import spring.annotation.PointCut;

/**
 * aop测试
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
@Aspect
public class AspectDemo {

    @PointCut
    String pointCut = "execution(* service.EnService.*(..))";

}
