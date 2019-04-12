package spring.ioc.reader;

import spring.ioc.factory.BeanDefinitionRegistry;

/**
 * 通过继承该类来实现自定义的配置读取
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    protected BeanDefinitionRegistry beanDefinitionRegistry;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {

        this.beanDefinitionRegistry = beanDefinitionRegistry;

    }

}
