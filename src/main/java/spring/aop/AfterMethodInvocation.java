package spring.aop;

/**
 * 后置通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class AfterMethodInvocation implements MethodInvocation {

    private MethodInvocation methodInvocation;

    public AfterMethodInvocation(MethodInvocation methodInvocation){
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke() {

        Object object = methodInvocation.invoke();
        System.out.println("后置通知");

        return object;
    }
}
