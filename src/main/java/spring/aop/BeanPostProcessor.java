package spring.aop;

/**
 * 在bean装载的时候执行
 *
 * @author tangzw
 * @date 2019-03-03
 * @since 1.0.0
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean) throws Exception;

    Object postProcessAfterInitialization(Object bean) throws Exception;
}
