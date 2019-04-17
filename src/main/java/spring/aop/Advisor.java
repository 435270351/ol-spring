package spring.aop;

import spring.common.enums.AdviceEnum;

import java.lang.reflect.Method;

/**
 * 保存通知相关方法
 *
 * @author tangzw
 * @date 2019-03-05
 * @since 1.0.0
 */
public class Advisor implements Comparable<Advisor> {

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

    /**
     * before，after，通知类型名
     */
    private String methodName;

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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public int compareTo(Advisor o) {
        AdviceEnum a1 = AdviceEnum.parse(methodName);
        AdviceEnum a2 = AdviceEnum.parse(o.getMethodName());

        return a1.getVal() - a2.getVal();
    }
}
