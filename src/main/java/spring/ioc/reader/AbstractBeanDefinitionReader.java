package spring.ioc.reader;

import spring.ioc.factory.BeanDefinitionRegistry;
import spring.ioc.factory.DefaultListableBeanFactory;

/**
 * 通过继承该类来实现自定义的配置读取
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    protected DefaultListableBeanFactory defaultListableBeanFactory;

    public AbstractBeanDefinitionReader(DefaultListableBeanFactory defaultListableBeanFactory) {

        this.defaultListableBeanFactory = defaultListableBeanFactory;

    }

}
