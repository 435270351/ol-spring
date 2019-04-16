package spring.ioc.factory;

import org.aspectj.lang.annotation.Around;
import spring.aop.Advisor;
import spring.aop.ClassMatcher;
import spring.aop.invocation.AspectJAfterAdvice;
import spring.aop.invocation.AspectJAroundAdvice;
import spring.aop.invocation.AspectJBeforeAdvice;
import spring.aop.invocation.MethodInterceptor;
import spring.common.annotation.After;
import spring.common.annotation.Before;
import spring.common.enums.AdviceEnum;

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
public class AdvisorChainFactory {

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport advisedSupport, Method method){
        List<Advisor> advisorList = advisedSupport.getAdvisorList();
        List<Object> chainList = new ArrayList<>();

        for (Advisor advisor : advisorList) {
            ClassMatcher classMatcher = advisor.getClassMatcher();
            if (classMatcher.matches(method)) {

                MethodInterceptor methodInterceptor = null;

                switch (advisor.getMethodName()){
                case "BEFORE":
                    methodInterceptor = new AspectJBeforeAdvice(advisor);
                    break;

                case "AROUND":
                    methodInterceptor = new AspectJAroundAdvice(advisor);
                    break;

                case "AFTER":
                    methodInterceptor = new AspectJAfterAdvice(advisor);
                    break;
                }

                chainList.add(methodInterceptor);
            }
        }

        return chainList;
    }
}
