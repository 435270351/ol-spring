package spring.aop.invocation;

/**
 * 方法执行，使用适配器模式，before、after、around
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public interface MethodInvocation {

    Object proceed() throws Exception;
}
