package spring.aop;

import spring.aop.invocation.MethodInvocation;

/**
 * 用来实现装饰模式
 *
 * @author tangzw
 * @date 2019-03-05
 * @since 1.0.0
 */
public abstract class AbstractMethodInvocation implements MethodInvocation {

    protected MethodInvocation methodInvocation;

    protected Advisor aopMethod;

    public AbstractMethodInvocation(MethodInvocation methodInvocation, Advisor aopMethod) {
        this.methodInvocation = methodInvocation;
        this.aopMethod = aopMethod;
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

}
