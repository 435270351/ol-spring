package spring.aop.invocation;

import spring.aop.Advisor;

/**
 * 环绕通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AspectJAroundAdvice implements MethodInterceptor {

    private Advisor advisor;

    public AspectJAroundAdvice(Advisor advisor) {
        this.advisor = advisor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Exception {

        try {
            Object object = advisor.getMethod().invoke(advisor.getTarget(), methodInvocation);

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
