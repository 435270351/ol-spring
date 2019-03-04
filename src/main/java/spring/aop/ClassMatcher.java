package spring.aop;

import java.lang.reflect.Method;

/**
 * 匹配关系
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public interface ClassMatcher {

    boolean matches(Method method);

    boolean matches(Class targetClass);
}
