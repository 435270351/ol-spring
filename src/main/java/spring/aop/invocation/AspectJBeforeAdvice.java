package spring.aop.invocation;

import spring.aop.Advisor;

/**
 * 前置通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AspectJBeforeAdvice implements MethodInterceptor {

    private Advisor advisor;

    public AspectJBeforeAdvice(Advisor advisor) {
        this.advisor = advisor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) {
        try {
            advisor.getMethod().invoke(advisor.getTarget());
            Object object = methodInvocation.proceed();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
