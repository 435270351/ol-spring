package spring.factory;

/**
 * 基础类
 *
 * @author tangzw
 * @date 2019-01-30
 * @since 1.0.0
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;
}
