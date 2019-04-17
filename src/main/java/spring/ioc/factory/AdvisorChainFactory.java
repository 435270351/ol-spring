package spring.ioc.factory;

import spring.aop.Advisor;
import spring.aop.ClassMatcher;
import spring.aop.invocation.AspectJAfterAdvice;
import spring.aop.invocation.AspectJAroundAdvice;
import spring.aop.invocation.AspectJBeforeAdvice;
import spring.aop.invocation.MethodInterceptor;
import spring.common.enums.AdviceEnum;
import spring.common.enums.AnnotationEnum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 通知链工厂
 *
 * @author tangzw
 * @date 2019-04-16
 * @since 1.0.0
 */
public class AdvisorChainFactory {

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport advisedSupport, Method method){
        List<Advisor> advisorList = advisedSupport.getAdvisorList();
        List<Object> chainList = new ArrayList<>();

        for (Advisor advisor : advisorList) {
            ClassMatcher classMatcher = advisor.getClassMatcher();
            // 检查方法是否需要去拦截
            if (classMatcher.matches(method)) {

                MethodInterceptor methodInterceptor = null;

                String methodName = advisor.getMethodName();

                if (AdviceEnum.BEFORE.name().equals(methodName)){
                    methodInterceptor = new AspectJBeforeAdvice(advisor);

                }else if (AdviceEnum.AROUND.name().equals(methodName)){
                    methodInterceptor = new AspectJAroundAdvice(advisor);

                }else if (AdviceEnum.AFTER.name().equals(methodName)){
                    methodInterceptor = new AspectJAfterAdvice(advisor);

                }

                chainList.add(methodInterceptor);
            }
        }

        return chainList;
    }
}
