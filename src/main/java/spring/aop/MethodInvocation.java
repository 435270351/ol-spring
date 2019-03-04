package spring.aop;

/**
 * 使用装饰模式来实现切入，before、after、around
 *
 * @author tangzw
 * @date 2019-03-04
 * @since 1.0.0
 */
public interface MethodInvocation {

    Object invoke();
}
