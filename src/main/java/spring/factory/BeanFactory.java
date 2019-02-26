package spring.factory;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-01-30
 * @since (版本)
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;
}
