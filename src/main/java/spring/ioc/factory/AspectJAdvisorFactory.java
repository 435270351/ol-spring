package spring.ioc.factory;

import spring.aop.Advisor;
import spring.aop.AspectJExpressionPointcut;
import spring.common.annotation.After;
import spring.common.annotation.Around;
import spring.common.annotation.Before;
import spring.common.enums.AdviceEnum;
import spring.ioc.bean.BeanDefinition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-16
 * @since (版本)
 */
public class AspectJAdvisorFactory {

    public List<Advisor> getAdvisors(BeanDefinition beanDefinition) {
        List<Advisor> advisorList = new ArrayList<>();

        if (beanDefinition.isAspect()) {

            try {
                Class clazz = beanDefinition.getBeanClass();

                Method[] methods = clazz.getDeclaredMethods();
                Object target = clazz.newInstance();

                // 通知装配
                for (Method method : methods) {

                    String methodName = "";
                    String pointCut = "";

                    // 前置通知
                    if (method.isAnnotationPresent(Before.class)) {
                        Before before = method.getDeclaredAnnotation(Before.class);
                        methodName = AdviceEnum.BEFORE.name();
                        pointCut = before.value();

                    } else if (method.isAnnotationPresent(Around.class)) {
                        Around around = method.getDeclaredAnnotation(Around.class);
                        methodName = AdviceEnum.AROUND.name();
                        pointCut = around.value();

                    } else if (method.isAnnotationPresent(After.class)) {
                        After after = method.getDeclaredAnnotation(After.class);
                        methodName = AdviceEnum.AFTER.name();
                        pointCut = after.value();

                    } else {
                        continue;
                    }

                    // 获取切点
                    String expression = pointCut;

                    // 声明切面操作
                    AspectJExpressionPointcut aspectPointCut = new AspectJExpressionPointcut();
                    aspectPointCut.setExpression(expression);

                    Advisor advisor = new Advisor();
                    advisor.setMethod(method);
                    advisor.setTarget(target);
                    advisor.setClassMatcher(aspectPointCut);
                    advisor.setMethodName(methodName);

                    advisorList.add(advisor);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return advisorList;
    }
}
