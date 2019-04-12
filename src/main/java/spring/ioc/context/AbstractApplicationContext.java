package spring.ioc.context;

import spring.ioc.factory.DefaultListableBeanFactory;

/**
 * 通过继承该类来实现自定义的配置读取
 *
 * @author tangzw
 * @date 2019-01-30
 * @since 1.0.0
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * refresh()起到了承上启下的作用，也是最关键的一步，用以定义加载的整个流程
     *
     * @author:tangzw
     * @date: 2019-02-27
     * @since v1.0.0
     */
    @Override
    public void refresh() {
        refreshBeanFactory();
    }

    public void refreshBeanFactory() {
        defaultListableBeanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions(defaultListableBeanFactory);
    }

    /**
     * 通过这个方法实现自定义的bean加载关系，获取到BeanDefinition关系需要添加到AbstractBeanFactory的beanDefinitionMap里
     * xml文件读取，注解读取等
     *
     * @author:tangzw
     * @date: 2019-02-27
     * @since v1.0.0
     * @param defaultListableBeanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory);

    @Override
    public Object getBean(String name) throws Exception {
        return defaultListableBeanFactory.getBean(name);
    }

}
