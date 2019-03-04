package spring.aop;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 装饰操作
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public interface OperationMethodInvocation {

    /**
     * 前置通知装饰
     *
     * @param method
     * @param target
     * @param classMatcherList
     * @return
     */
    MethodInvocation beforeMethodInvocation(Method method, MethodInvocation target, List<ClassMatcher> classMatcherList);

    /**
     * 环绕通知装饰
     *
     * @param method
     * @param target
     * @param classMatcherList
     * @return
     */
    MethodInvocation aroundMethodInvocation(Method method, MethodInvocation target, List<ClassMatcher> classMatcherList);

    /**
     * 后置通知装饰
     *
     * @param method
     * @param target
     * @param classMatcherList
     * @return
     */
    MethodInvocation afterMethodInvocation(Method method, MethodInvocation target, List<ClassMatcher> classMatcherList);
}
