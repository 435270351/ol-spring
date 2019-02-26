package demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-20
 * @since (版本)
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = method.invoke(target, args);
        System.out.println("xm");

        return object;
    }

}
