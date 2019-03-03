package spring.aop;

/**
 * 匹配类
 *
 * @author tangzw
 * @date 2019-03-03
 * @since (版本)
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
