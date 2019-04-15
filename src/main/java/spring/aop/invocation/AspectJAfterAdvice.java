package spring.aop.invocation;

import spring.aop.Advisor;

/**
 * 后置通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AspectJAfterAdvice implements MethodInterceptor {

    private Advisor advisor;

    public AspectJAfterAdvice(Advisor advisor) {
        this.advisor = advisor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) {

        try {
            Object object = methodInvocation.proceed();

            advisor.getMethod().invoke(advisor.getTarget());

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
