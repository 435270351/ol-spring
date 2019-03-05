package spring.aop;

/**
 * 后置通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AfterMethodInvocation extends AbstractMethodInvocation {

    public AfterMethodInvocation(MethodInvocation methodInvocation, AopMethod aopMethod) {
        super(methodInvocation, aopMethod);
    }

    @Override
    public Object invoke() {

        try {
            Object object = getMethodInvocation().invoke();
            aopMethod.getMethod().invoke(aopMethod.getTarget());

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
