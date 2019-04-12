package spring.aop;

import spring.ioc.factory.DefaultListableBeanFactory;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-03-03
 * @since (版本)
 */
public interface BeanFactoryAware {

    void setBeanFactory(DefaultListableBeanFactory defaultListableBeanFactory) throws Exception;
}
