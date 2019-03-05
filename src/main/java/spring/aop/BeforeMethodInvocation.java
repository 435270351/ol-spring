package spring.aop;

/**
 * 前置通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class BeforeMethodInvocation extends AbstractMethodInvocation {

    public BeforeMethodInvocation(MethodInvocation methodInvocation, AopMethod aopMethod) {
        super(methodInvocation, aopMethod);
    }

    @Override
    public Object invoke() {
        try {
            aopMethod.getMethod().invoke(aopMethod.getTarget());
            Object object = getMethodInvocation().invoke();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
