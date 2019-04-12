package spring.ioc.factory;

import spring.ioc.bean.BeanDefinition;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-04-11
 * @since (版本)
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String key, BeanDefinition val);
}
