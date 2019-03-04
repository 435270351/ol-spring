package spring.aop;

/**
 * 环绕通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AroundMethodInvocation implements MethodInvocation {

    private MethodInvocation methodInvocation;

    public AroundMethodInvocation(MethodInvocation methodInvocation){
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke() {
        System.out.println("环绕通知Start");
        Object object = methodInvocation.invoke();
        System.out.println("环绕通知End");

        return object;
    }
}
