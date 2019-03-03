package spring.aop;

import java.lang.reflect.Method;

/**
 * 匹配方法
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public interface MethodMatcher {
    boolean matches(Method method);
}
