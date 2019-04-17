package spring.ioc.factory;

import spring.ioc.bean.BeanDefinition;

/**
 * bean关系注册
 *
 * @author tangzw
 * @date 2019-04-11
 * @since 1.0.0
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String key, BeanDefinition val);
}
