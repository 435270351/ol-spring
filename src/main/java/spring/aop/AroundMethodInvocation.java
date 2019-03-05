package spring.aop;

/**
 * 环绕通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AroundMethodInvocation extends AbstractMethodInvocation {

    public AroundMethodInvocation(MethodInvocation methodInvocation, AopMethod aopMethod) {
        super(methodInvocation, aopMethod);
    }

    @Override
    public Object invoke() {

        try {
            Object object = aopMethod.getMethod().invoke(aopMethod.getTarget(),methodInvocation);

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
