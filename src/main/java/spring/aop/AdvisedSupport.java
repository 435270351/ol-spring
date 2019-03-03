package spring.aop;

import lombok.Data;

/**
 * 代理相关元素
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public class AdvisedSupport {

    private TargetSource targetSource;

    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
