package spring.aop;

import java.lang.reflect.Method;

/**
 * 方法执行
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public class CglibMethodInvocation implements MethodInvocation {

    private Method method;

    private TargetSource targetSource;

    private Object[] params;

    public CglibMethodInvocation(Method method, TargetSource targetSource, Object[] params) {
        this.method = method;
        this.targetSource = targetSource;
        this.params = params;
    }

    @Override
    public Object invoke() {
        Object object = null;
        try {
            object = method.invoke(targetSource.getTarget(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

}
