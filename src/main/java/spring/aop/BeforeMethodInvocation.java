package spring.aop;

/**
 * 前置通知
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class BeforeMethodInvocation implements MethodInvocation {

    private MethodInvocation methodInvocation;

    public BeforeMethodInvocation(MethodInvocation methodInvocation){
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke() {
        System.out.println("前置通知");
        Object object = methodInvocation.invoke();

        return object;
    }

}
