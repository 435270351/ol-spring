package spring.reader;

import spring.factory.AbstractBeanFactory;

/**
 * 通过继承该类来实现自定义的配置读取
 *
 * @author tangzw
 * @date 2019-02-26
 * @since 1.0.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    /**
     * 读取地址路径
     */
    protected String[] locations;

    protected AbstractBeanFactory abstractBeanFactory;

    public AbstractBeanDefinitionReader(String[] locations, AbstractBeanFactory abstractBeanFactory) {
        for (int i = 0; i < locations.length; i++) {
            String location = locations[i].replace('.', '/');
            location = this.getClass().getClassLoader().getResource("").getPath()+ location;

            locations[i] = location;
        }

        this.locations = locations;
        this.abstractBeanFactory = abstractBeanFactory;

    }

}
