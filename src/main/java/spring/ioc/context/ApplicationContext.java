package spring.ioc.context;

import spring.ioc.factory.BeanFactory;

/**
 * ApplicationContext
 *
 * @author tangzw
 * @date 2019-01-30
 * @since 1.0.0
 */
public interface ApplicationContext extends BeanFactory {

    /**
     * 用以定义加载的整个流程
     *
     * @author:tangzw
     * @date: 2019-04-11
     * @since v1.0.0
     */
    void refresh();
}
