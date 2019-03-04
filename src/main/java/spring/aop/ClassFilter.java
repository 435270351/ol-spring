package spring.aop;

/**
 * 匹配类
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
