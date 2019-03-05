package spring.aop;

import java.lang.reflect.Method;

/**
 * 切面方法
 *
 * @author tangzw
 * @date 2019-03-05
 * @since 1.0.0
 */
public class AopMethod {

    /**
     * 匹配关系
     */
    private ClassMatcher classMatcher;

    /**
     * 切面方法
     */
    private Method method;

    /**
     * 切面对象
     */
    private Object target;

    public ClassMatcher getClassMatcher() {
        return classMatcher;
    }

    public void setClassMatcher(ClassMatcher classMatcher) {
        this.classMatcher = classMatcher;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
