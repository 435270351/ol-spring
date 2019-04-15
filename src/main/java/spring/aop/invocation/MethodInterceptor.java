package spring.aop.invocation;

/**
 * 方法执行
 *
 * @author tangzw
 * @date 2019-04-15
 * @since (版本)
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation methodInvocation) throws Exception;
}
